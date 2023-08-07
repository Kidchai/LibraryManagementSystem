package kidchai.library.management.controllers;

import jakarta.validation.Valid;
import kidchai.library.management.dto.book.BookDTOForBook;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.BooksService;
import kidchai.library.management.services.mappers.BookMapperService;
import kidchai.library.management.util.assemblers.BookModelAssembler;
import kidchai.library.management.util.book.BookErrorResponse;
import kidchai.library.management.util.book.BookNotCreatedException;
import kidchai.library.management.util.book.BookNotFoundException;
import kidchai.library.management.util.book.BookNotUpdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;
    private final BookMapperService mapperService;
    private final BookModelAssembler assembler;

    @Autowired
    public BooksController(BooksService booksService, BookMapperService mapperService, BookModelAssembler assembler) {
        this.booksService = booksService;
        this.mapperService = mapperService;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<BookDTOForBook>> index(@RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "title", required = false) String title,
                                                              @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                                                              @RequestParam(value = "sort_by_year", required = false) boolean isSortedByYear) {
        List<Book> books;
        if (title != null)
            books = booksService.findByTitle(title);
        else {
            if (page == null && booksPerPage == null)
                books = booksService.findAll(isSortedByYear);
            else
                books = booksService.findAll(page, booksPerPage, isSortedByYear);
        }

        List<EntityModel<BookDTOForBook>> booksDTO = books.stream()
                .map(mapperService::convertToDTO)
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(booksDTO,
                linkTo(methodOn(BooksController.class).index(page, title, booksPerPage, isSortedByYear)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<BookDTOForBook> show(@PathVariable("id") int id) {
        BookDTOForBook book = mapperService.convertToDTO(booksService.findOne(id));
        return assembler.toModel(book);
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
        BookErrorResponse error = new BookErrorResponse(exception.getMessage(), System.currentTimeMillis());
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
