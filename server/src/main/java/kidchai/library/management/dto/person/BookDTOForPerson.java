package kidchai.library.management.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTOForPerson {
    private int id;
    private String title;
    private String author;
    private int year;
    private Date takenAt;
}
