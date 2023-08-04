package kidchai.library.management.services.mappers;

import kidchai.library.management.dto.person.BookDTOForPerson;
import kidchai.library.management.dto.person.PersonDTOForPerson;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonMapperService {
    public PersonDTOForPerson convertToDTO(Person person) {
        List<BookDTOForPerson> bookDTOForPeople = person.getBooks()
                .stream()
                .map(this::getDTOBook)
                .toList();

        return new PersonDTOForPerson(person.getId(), person.getName(), person.getBirthYear(), bookDTOForPeople);
    }

    private BookDTOForPerson getDTOBook(Book book) {
        return new BookDTOForPerson(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), book.getTakenAt());
    }
}
