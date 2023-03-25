package kidchai.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name should be not empty")
    private String name;

    @Column(name = "birth_year")
    @NotNull(message = "Year of birth should be not empty")
    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    @Max(value = 2023, message = "Year of birth should be less than 2023")
    private int birthYear;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private List<Book> books;

    public Person() {
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
