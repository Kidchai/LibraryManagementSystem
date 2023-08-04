package kidchai.library.management.dto.book;

import java.util.Date;

public class PersonDTOForBook {
    private int id;
    private String name;
    private Date birthYear;

    public PersonDTOForBook() {
    }

    public PersonDTOForBook(int id, String name, Date birthYear) {
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

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }
}
