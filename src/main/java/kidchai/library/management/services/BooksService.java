package kidchai.library.management.services;

import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.repositories.BooksRepository;
import kidchai.library.management.util.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean isSortedByYear) {
        return (isSortedByYear) ?
                booksRepository.findAll(Sort.by("year")) :
                booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean isSortedByYear) {
        return (isSortedByYear) ?
                booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent() :
                booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElseThrow(BookNotFoundException::new);
    }

    public List<Book> findByTitle(String title) {
        Optional<List<Book>> books = booksRepository.findAllByTitleStartingWith(title);
        return books.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return;
        }
        Book targetBook = optionalBook.get();
        targetBook.setHolder(null);
        booksRepository.save(targetBook);
    }

    @Transactional
    public void assign(int id, Person person) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return;
        }
        Book targetBook = optionalBook.get();
        targetBook.setHolder(person);
        Date today = new Date();
        targetBook.setTakenAt(today);
        booksRepository.save(targetBook);
    }
}
