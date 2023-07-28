package kidchai.library.management.controllers;

import jakarta.validation.Valid;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.BooksService;
import kidchai.library.management.util.book.BookErrorResponse;
import kidchai.library.management.util.book.BookNotCreatedException;
import kidchai.library.management.util.book.BookNotFoundException;
import kidchai.library.management.util.book.BookNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> index(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                                            @RequestParam(value = "sort_by_year", required = false) boolean isSortedByYear) {
        if (title != null)
            return ResponseEntity.ok(booksService.findByTitle(title));

        List<Book> books = (page == null && booksPerPage == null) ?
                booksService.findAll(isSortedByYear) :
                booksService.findAllWithPagination(page, booksPerPage, isSortedByYear);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> show(@PathVariable("id") int id) {
        return ResponseEntity.ok(booksService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<Book> create(@RequestBody @Valid Book book,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = getExceptionMessage(bindingResult);
            throw new BookNotCreatedException(errors);
        }
        return ResponseEntity.ok(booksService.save(book));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> update(@RequestBody @Valid Book book, BindingResult bindingResult,
                                       @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            String errors = getExceptionMessage(bindingResult);
            throw new BookNotUpdatedException(errors);
        }
        return ResponseEntity.ok(booksService.update(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<Book> release(@PathVariable("id") int id) {
        return ResponseEntity.ok(booksService.release(id));
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Book> assign(@PathVariable("id") int id, @RequestBody Person person) {
        return ResponseEntity.ok(booksService.assign(id, person));

    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
        BookErrorResponse error = new BookErrorResponse(
                "Book with this id not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotCreatedException exception) {
        BookErrorResponse error = new BookErrorResponse(exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotUpdatedException exception) {
        BookErrorResponse error = new BookErrorResponse(exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String getExceptionMessage(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(error -> errors
                        .append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("\n"));
        return errors.toString();
    }
}
