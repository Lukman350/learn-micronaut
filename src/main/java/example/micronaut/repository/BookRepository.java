package example.micronaut.repository;

import example.micronaut.domain.Book;
import example.micronaut.util.SortingAndOrderArguments;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(long id);

    Book save(@NotBlank String name, @NotBlank String creator, @NotBlank String genreName);

    Book saveWithException(@NotBlank String name, @NotBlank String creator, @NotBlank String genreName);

    void deleteById(long id);

    List<Book> findAll(@NotNull SortingAndOrderArguments args);

    int update(long id, @NotBlank String name, @NotBlank String creator, @Nullable @NotBlank String genreName);
}