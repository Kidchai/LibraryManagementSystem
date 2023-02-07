package kidchai.dao;

import kidchai.models.Book;
import kidchai.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT Book.*, Person.name as owner FROM Book LEFT OUTER JOIN person ON Person.id = Book.user_id WHERE book.id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

//    public void showIsAvailable(int id) {
//        int userId = jdbcTemplate.query("SELECT user_id FROM Book WHERE id=?", new Object[]{id},
//                        new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny().get().getUserId();
//        if (userId == 0) {
//            return "Available";
//        }
//        return jdbcTemplate.query("SELECT name FROM Person WHERE id=?", new Object[]{userId},
//                new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().get().getName();
//    }
}
