package example.micronaut.controller;

import example.micronaut.command.BookSaveCommand;
import example.micronaut.command.BookUpdateCommand;
import example.micronaut.domain.Book;
import example.micronaut.repository.BookRepository;
import example.micronaut.util.SortingAndOrderArguments;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static io.micronaut.http.HttpHeaders.LOCATION;

@ExecuteOn(TaskExecutors.IO)
@Controller("/books")
class BookController {

    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Get("/{id}")
    Book show(Long id) {
        return bookRepository
                .findById(id)
                .orElse(null);
    }

    @Put
    HttpResponse<?> update(@Body @Valid BookUpdateCommand command) {
        int numberOfEntitiesUpdated = bookRepository.update(command.getId(), command.getName(), command.getCreator(), command.getGenreName());

        return HttpResponse
                .noContent()
                .header(LOCATION, location(command.getId()).getPath());
    }

    @Get(value = "/list{?args*}")
    List<Book> list(@Valid SortingAndOrderArguments args) {
        return bookRepository.findAll(args);
    }

    @Post
    HttpResponse<Book> save(@Body @Valid BookSaveCommand cmd) {
        Book book = bookRepository.save(cmd.getName(), cmd.getCreator(), cmd.getGenreName());

        return HttpResponse
                .created(book)
                .headers(headers -> headers.location(location(book.getId())));
    }

    @Post("/ex")
    HttpResponse<Book> saveExceptions(@Body @Valid BookSaveCommand cmd) {
        try {
            Book book = bookRepository.saveWithException(cmd.getName(), cmd.getCreator(), cmd.getGenreName());
            return HttpResponse
                    .created(book)
                    .headers(headers -> headers.location(location(book.getId())));
        } catch(PersistenceException e) {
            return HttpResponse.noContent();
        }
    }

    @Delete("/{id}")
    HttpResponse<?> delete(Long id) {
        bookRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    private URI location(Long id) {
        return URI.create("/books/" + id);
    }
}