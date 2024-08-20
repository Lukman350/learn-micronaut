package example.micronaut.command;

import example.micronaut.domain.Genre;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotBlank;

@Serdeable
public class BookSaveCommand {

    @NotBlank
    private String name;

    @NotBlank
    private String creator;

    @Nullable
    private String genreName;

    public BookSaveCommand(String name, String creator, String genreName) {
        this.name = name;
        this.creator = creator;
        this.genreName = genreName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotBlank String getCreator() {
        return creator;
    }

    public void setCreator(@NotBlank String creator) {
        this.creator = creator;
    }

    @Nullable
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(@Nullable String genreName) {
        this.genreName = genreName;
    }
}