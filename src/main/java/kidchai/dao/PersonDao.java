package kidchai.dao;

import kidchai.models.Book;
import kidchai.models.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Optional<Person> show(int id, String name, int birthYear) {
//        return jdbcTemplate.query("SELECT * FROM people WHERE id!=? AND name=? AND birth_year=?",
//                        new Object[]{id, name, birthYear}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny();
//    }
//
//    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM people WHERE people.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny().orElse(null);
//    }
//
//    public void save(Person person) {
//        jdbcTemplate.update("INSERT INTO people(name, birth_year) VALUES (?, ?)", person.getName(), person.getBirthYear());
//    }
//
//    public void update(int id, Person updatedPerson) {
//        jdbcTemplate.update("UPDATE people SET name=?, birth_year=? WHERE id=?",
//                updatedPerson.getName(), updatedPerson.getBirthYear(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM people WHERE id=?", id);
//    }
//
//    public List<Book> getPersonBooks(int id) {
//        return jdbcTemplate.query("SELECT * FROM books WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
//    }
}
