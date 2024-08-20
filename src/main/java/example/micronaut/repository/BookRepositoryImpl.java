package example.micronaut.repository;

import example.micronaut.ApplicationConfig;
import example.micronaut.domain.Book;
import example.micronaut.domain.Genre;
import example.micronaut.util.SortingAndOrderArguments;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class BookRepositoryImpl implements BookRepository {

    private static final List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name", "creator");

    private final EntityManager entityManager;
    private final ApplicationConfig applicationConfiguration;

    public BookRepositoryImpl(EntityManager entityManager,
                               ApplicationConfig applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @ReadOnly
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    @Transactional
    public Book save(@NotBlank String name, @NotBlank String creator, @NotBlank String genreName) {
        Genre genre = new Genre(genreName);
        Book book = new Book(creator, name, genre);
        book.setGenre(genre);
        entityManager.persist(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @ReadOnly
    public List<Book> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT b FROM Book as b";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY b." + args.getSort().get() + ' ' + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Book> query = entityManager.createQuery(qlString, Book.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(long id, @NotBlank String name, @NotBlank String creator, @Nullable @NotBlank String genreName) {
        GenreRepository genreRepository = new GenreRepositoryImpl(entityManager, applicationConfiguration);

        if (!genreName.isBlank() && !genreName.isEmpty()) {
            List<Genre> genre = genreRepository.findByName(genreName);
            long genreId = 0;

            if (genre.isEmpty()) {
                Genre newGenre = genreRepository.save(genreName);
                genreId = newGenre.getId();
            }

            return entityManager.createQuery("UPDATE Book b SET name = :name, creator = :creator, genre_id = :genre where id = :id")
                    .setParameter("name", name)
                    .setParameter("creator", creator)
                    .setParameter("genre", !genre.isEmpty() ? genre.get(0).getId() : genreId)
                    .setParameter("id", id)
                    .executeUpdate();
        }

        return entityManager.createQuery("UPDATE Book b SET name = :name, creator = :creator where id = :id")
                .setParameter("name", name)
                .setParameter("creator", creator)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional
    public Book saveWithException(@NotBlank String name, @NotBlank String creator, @NotBlank String genreName) {
        save(name, creator, genreName);
        throw new PersistenceException();
    }
}