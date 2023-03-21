package kidchai.controllers;

import javax.validation.Valid;
import kidchai.dao.BookDao;
import kidchai.dao.PersonDao;
import kidchai.models.Book;
import kidchai.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
//        model.addAttribute("book", bookDao.show(id));
//        Person bookHolder = bookDao.getHolder(id);
//        if (bookHolder != null) {
//            model.addAttribute("holder", bookHolder);
//        } else {
//            model.addAttribute("people", personDao.index());
//        }
//        return "books/show";
//    }
//
//    @GetMapping("/new")
//    public String newBook(@ModelAttribute("book") @Valid Book book) {
//        return "books/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("book") @Valid Book book) {
//        bookDao.save(book);
//        return "redirect:/books";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") int id, Model model) {
//        model.addAttribute("book", bookDao.show(id));
//        return "books/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id) {
//        bookDao.update(id, book);
//        return "redirect:/books";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        bookDao.delete(id);
//        return "redirect:/books";
//    }
//
//    @PatchMapping("/{id}/release")
//    public String release(@PathVariable("id") int id) {
//        bookDao.release(id);
//        return "redirect:/books/" + id;
//    }
//
//    @PatchMapping("/{id}/assign")
//    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
//        bookDao.assign(id, person);
//        return "redirect:/books/" + id;
//    }
}
