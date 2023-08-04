package kidchai.library.management.dto.person;

import java.util.Date;
import java.util.List;

public class PersonDTOForPerson {
    private int id;
    private String name;
    private Date birthYear;
    private List<BookDTOForPerson> books;

    public PersonDTOForPerson(int id, String name, Date birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public PersonDTOForPerson(int id, String name, Date birthYear, List<BookDTOForPerson> books) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.books = books;
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

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }

    public List<BookDTOForPerson> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTOForPerson> books) {
        this.books = books;
    }
}
