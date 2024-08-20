package example.micronaut.domain;

import io.micronaut.serde.annotation.Serdeable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "creator", nullable = false)
    private String creator;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Genre genre;

    public Book() {}

    public Book(@NotNull String creator,
                @NotNull String name,
                Genre genre) {
        this.creator = creator;
        this.name = name;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", genre=" + genre +
                '}';
    }
}