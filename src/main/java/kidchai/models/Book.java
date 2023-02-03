package kidchai.models;

import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @NotEmpty(message = "Title should be not empty")
    private String title;

    @NotEmpty(message = "Author should be not empty")
    private String author;

    @NotEmpty(message = "Year of publication should be not empty")
    private int year;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int birthYear) {
        this.year = birthYear;
    }
}
