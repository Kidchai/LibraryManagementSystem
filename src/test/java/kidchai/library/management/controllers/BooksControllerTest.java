package kidchai.library.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.BooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BooksControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private BooksService booksService;

    @Autowired
    public BooksControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testIndex() throws Exception {
        Book book1 = new Book();
        Book book2 = new Book();

        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(booksService.findAll(false)).thenReturn(expectedBooks);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        Book expectedBook = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);
        expectedBook.setId(1);

        int bookId = 1;
        when(booksService.findOne(bookId)).thenReturn(expectedBook);

        mockMvc.perform(get("/api/books/" + bookId))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Book newBook = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk());

        verify(booksService, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdate() throws Exception {
        int bookId = 1;
        Book book = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);
        book.setId(bookId);

        mockMvc.perform(patch("/api/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());

        verify(booksService, times(1)).update(eq(bookId), any(Book.class));
    }

    @Test
    void testDelete() throws Exception {
        int bookId = 1;

        mockMvc.perform(delete("/api/books/" + bookId)
                        .flashAttr("bookId", bookId))
                .andExpect(status().isOk());

        verify(booksService, times(1)).delete(bookId);
    }

    @Test
    void testRelease() throws Exception {
        int bookId = 1;

        mockMvc.perform(patch("/api/books/" + bookId + "/release")
                        .flashAttr("bookId", bookId))
                .andExpect(status().isOk());

        verify(booksService, times(1)).release(bookId);
    }

    @Test
    void assign() throws Exception {
        int bookId = 1;
        Person person = new Person();
        person.setId(1);
        person.setName("John Doe");

        mockMvc.perform(patch("/api/books/" + bookId + "/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(booksService, times(1)).assign(eq(bookId), any(Person.class));
    }
}