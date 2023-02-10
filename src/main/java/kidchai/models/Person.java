package kidchai.models;

import jakarta.validation.constraints.NotEmpty;

public class Person {
    private int id;
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @NotEmpty(message = "Year of birth should be not empty")
    private int birthYear;

    private String book;

    public Person() {
    }

    public Person(int id, String name, int birthYear) {
        this.id = id;
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

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String books) {
        this.book = books;
    }
}
