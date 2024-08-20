package example.micronaut.command;

import example.micronaut.domain.Genre;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotBlank;

@Serdeable
public class BookUpdateCommand {

    private long id;

    @Nullable
    @NotBlank
    private String name;

    @Nullable
    @NotBlank
    private String creator;

    @Nullable
    private String genreName;

    public BookUpdateCommand(long id, String name, String creator, String genreName) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.genreName = genreName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public @NotBlank String getCreator() {
        return creator;
    }

    public void setCreator(@Nullable @NotBlank String creator) {
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