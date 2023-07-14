package kidchai.library.management.controllers;

import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.BooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
class BooksControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @Autowired
    BooksControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testIndex() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();

        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(booksService.findAll(false)).thenReturn(expectedBooks);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/index"))
                .andExpect(model().attribute("books", expectedBooks));
    }

    @Test
    public void testShow() throws Exception {
        Book expectedBook = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);
        expectedBook.setId(1);

        int bookId = 1;
        when(booksService.findOne(bookId)).thenReturn(expectedBook);

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("books/show"))
                .andExpect(model().attribute("book", expectedBook));
    }

    @Test
    public void testCreate() throws Exception {
        Book newBook = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);

        mockMvc.perform(post("/books")
                        .flashAttr("book", newBook))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(booksService, times(1)).save(newBook);
    }

    @Test
    void testEdit() throws Exception {
        int bookId = 1;
        Book book = new Book();
        book.setId(bookId);
        when(booksService.findOne(bookId)).thenReturn(book);

        mockMvc.perform(get("/books/" + bookId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", book));
    }

    @Test
    void testUpdate() throws Exception {
        int bookId = 1;
        Book book = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);
        book.setId(bookId);

        mockMvc.perform(patch("/books/" + bookId)
                        .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(booksService, times(1)).update(bookId, book);
    }

    @Test
    void testDelete() throws Exception {
        int bookId = 1;

        mockMvc.perform(delete("/books/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(booksService, times(1)).delete(bookId);
    }

    @Test
    void testRelease() throws Exception {
        int bookId = 1;

        mockMvc.perform(patch("/books/" + bookId + "/release"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + bookId));

        verify(booksService, times(1)).release(bookId);
    }

    @Test
    void assign() throws Exception {
        int bookId = 1;
        Person person = new Person();
        person.setId(1);
        person.setName("John Doe");

        mockMvc.perform(patch("/books/" + bookId + "/assign")
                        .flashAttr("person", person))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + bookId));

        verify(booksService, times(1)).assign(bookId, person);
    }

    @Test
    void search() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();

        book1.setTitle("The Catcher in the Rye");
        book2.setTitle("A Farewell to Arms");

        List<Book> books = Arrays.asList(book1, book2);

        String title = "A Farewell";
        when(booksService.findByTitle(title)).thenReturn(books);

        mockMvc.perform(get("/books/search")
                        .flashAttr("title", title))
                .andExpect(status().isOk())
                .andExpect(view().name("books/search"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books));
    }
}