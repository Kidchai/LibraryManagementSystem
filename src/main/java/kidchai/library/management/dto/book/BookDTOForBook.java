package kidchai.library.management.dto.book;

import java.util.Date;

public class BookDTOForBook {
    private int id;
    private String title;
    private String author;
    private int year;
    private PersonDTOForBook holder;
    private Date takenAt;

    public BookDTOForBook() {
    }

    public BookDTOForBook(int id, String title, String author, int year, PersonDTOForBook holder, Date takenAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.holder = holder;
        this.takenAt = takenAt;
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

    public void setYear(int year) {
        this.year = year;
    }

    public PersonDTOForBook getHolder() {
        return holder;
    }

    public void setHolder(PersonDTOForBook holder) {
        this.holder = holder;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
}
