package kidchai.library.management.controllers;

import jakarta.validation.Valid;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.BooksService;
import kidchai.library.management.util.BookErrorResponse;
import kidchai.library.management.util.BookNotCreatedException;
import kidchai.library.management.util.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public List<Book> index(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                            @RequestParam(value = "sort_by_year", required = false) boolean isSortedByYear) {
        return (page == null && booksPerPage == null) ?
                booksService.findAll(isSortedByYear) :
                booksService.findAllWithPagination(page, booksPerPage, isSortedByYear);
    }

    @GetMapping("/{id}")
    public Book show(@PathVariable("id") int id) {
        return booksService.findOne(id);
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Book book,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors()
                    .forEach(error -> errors
                            .append(error.getField())
                            .append(" - ")
                            .append(error.getDefaultMessage())
                            .append("\n"));

            throw new BookNotCreatedException(errors.toString());
        }
        booksService.save(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assign(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("title") String title) {
        if (title.isEmpty()) {
            return "books/search";
        }
        List<Book> books = booksService.findByTitle(title);
        model.addAttribute("books", books);
        if (books.isEmpty()) {
            model.addAttribute("bookNotFound", "");
        } else {
            model.addAttribute("books", books);
        }
        return "books/search";
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
        BookErrorResponse error = new BookErrorResponse(
                "Book with this id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotCreatedException exception) {
        BookErrorResponse error = new BookErrorResponse(exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
