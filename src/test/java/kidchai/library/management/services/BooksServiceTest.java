package kidchai.library.management.services;

import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.repositories.BooksRepository;
import kidchai.library.management.util.book.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;

    @Test
    void testFindAll() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(booksRepository.findAll(Sort.by("year"))).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = booksService.findAll(true);

        assertEquals(2, result.size());
        verify(booksRepository, times(1)).findAll(Sort.by("year"));
    }

    @Test
    void testFindAllWithPagination() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(booksRepository.findAll(PageRequest.of(0, 2, Sort.by("year"))))
                .thenReturn(new PageImpl<>(Arrays.asList(book1, book2)));

        List<Book> result = booksService.findAll(0, 2, true);

        assertEquals(2, result.size());
        verify(booksRepository, times(1))
                .findAll(PageRequest.of(0, 2, Sort.by("year")));
    }

    @Test
    void testFindOne() {
        Book book = new Book();
        when(booksRepository.findById(anyInt())).thenReturn(Optional.of(book));

        Book result = booksService.findOne(1);

        assertEquals(book, result);
        verify(booksRepository, times(1)).findById(1);
    }

    @Test
    void testFindOneNotFound() {
        when(booksRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            booksService.findOne(1);
        });

        String expectedMessage = "Book with this id not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(booksRepository, times(1)).findById(1);
    }

    @Test
    void testFindByTitle() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(booksRepository.findAllByTitleStartingWith(anyString()))
                .thenReturn(Optional.of(Arrays.asList(book1, book2)));

        List<Book> result = booksService.findByTitle("Title");

        assertEquals(2, result.size());
        verify(booksRepository, times(1)).findAllByTitleStartingWith("Title");
    }

    @Test
    void testFindByTitleNotFound() {
        when(booksRepository.findAllByTitleStartingWith(anyString()))
                .thenReturn(Optional.empty());

        List<Book> result = booksService.findByTitle("Title");

        assertNull(result);
        verify(booksRepository, times(1)).findAllByTitleStartingWith("Title");
    }

    @Test
    void testSave() {
        Book book = new Book();
        when(booksRepository.save(any(Book.class))).thenReturn(book);

        booksService.save(book);

        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void testUpdate() {
        Book book = new Book();
        book.setId(1);
        when(booksRepository.save(any(Book.class))).thenReturn(book);

        booksService.update(1, book);

        verify(booksRepository, times(1)).save(book);
    }

    @Test
    public void testDelete() {
        booksService.delete(1);
        verify(booksRepository, times(1)).deleteById(1);
    }

    @Test
    public void testRelease() {
        Book book = new Book();
        when(booksRepository.findById(anyInt())).thenReturn(Optional.of(book));

        booksService.release(1);

        verify(booksRepository, times(1)).save(book);
        verify(booksRepository, times(1)).findById(1);
        assertNull(book.getHolder());
    }

    @Test
    public void testAssign() {
        Book book = new Book();
        Person person = new Person();
        when(booksRepository.findById(anyInt())).thenReturn(Optional.of(book));

        booksService.assign(1, person);

        verify(booksRepository, times(1)).save(book);
        verify(booksRepository, times(1)).findById(1);
        assertEquals(person, book.getHolder());
        assertNotNull(book.getTakenAt());
    }
}