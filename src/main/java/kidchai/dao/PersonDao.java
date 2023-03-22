package kidchai.dao;

import kidchai.models.Book;
import kidchai.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }
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

    @Transactional
    public List<Book> getPersonBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        var query = session.createQuery("select b from Book b where b.userId=:i", Book.class);
        query.setParameter("i",id);
        return query.getResultList();
    }
}
