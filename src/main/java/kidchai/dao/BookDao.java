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
public class BookDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }


    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book targetBook = session.get(Book.class, id);
        targetBook.setTitle(updatedBook.getTitle());
        targetBook.setAuthor(updatedBook.getAuthor());
        targetBook.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

//    @Transactional(readOnly = true)
//    public Optional<Person> getHolder(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("select p from Book b join Person p on p.id=b.personId where b.id=:id", Person.class)
//                .setParameter("id", id)
//                .getResultList()
//                .stream().findAny();
//    }

    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book targetBook = session.get(Book.class, id);
        targetBook.setHolder(null);
    }

    @Transactional
    public void assign(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book targetBook = session.get(Book.class, id);
        targetBook.setHolder(person);
    }
}
