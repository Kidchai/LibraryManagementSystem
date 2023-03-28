package kidchai.controllers;

import kidchai.dao.BookDao;
import kidchai.models.Book;
import kidchai.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;

    public BooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDao.show(id);
        model.addAttribute("book", book);
        Person holder = book.getHolder();
//        if (holder == null) {
//            model.addAttribute("people", personDao.index());
//        } else {
//            model.addAttribute("holder", holder);
//        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDao.assign(id, person);
        return "redirect:/books/" + id;
    }
}
