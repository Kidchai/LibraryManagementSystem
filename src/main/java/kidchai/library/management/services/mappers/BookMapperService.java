package kidchai.library.management.services.mappers;

import kidchai.library.management.dto.book.BookDTOForBook;
import kidchai.library.management.dto.book.PersonDTOForBook;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import org.springframework.stereotype.Service;

@Service
public class BookMapperService {
    public BookDTOForBook convertToDTO(Book book) {
        Person holder = book.getHolder();
        PersonDTOForBook dtoHolder = holder == null ? null : getDTOHolder(holder);
        return new BookDTOForBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear(), dtoHolder, book.getTakenAt());
    }

    private PersonDTOForBook getDTOHolder(Person person) {
        return new PersonDTOForBook(person.getId(), person.getName(), person.getBirthYear());
    }
}
