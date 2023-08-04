package kidchai.library.management.dto.person;

import java.util.Date;

public class BookDTOForPerson {
    private int id;
    private String title;
    private String author;
    private int year;
    private Date takenAt;

    public BookDTOForPerson(int id, String title, String author, int year, Date takenAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
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

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
}
