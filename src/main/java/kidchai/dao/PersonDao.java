package kidchai.dao;

import kidchai.models.Book;
import kidchai.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.query.Query;
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

//    @Transactional(readOnly = true)
//    public Person show(int id, String name, int birthYear) {
//        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery("select p from Person p where p.id=:i and p.name=:n and p.birthYear=:y", Book.class);
//        query.setParameter("i",id);
//        query.setParameter("n",name);
//        query.setParameter("y",birthYear);
//        return (Person) query.getSingleResult();
//    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person targetPerson = session.get(Person.class, id);
        targetPerson.setName(updatedPerson.getName());
        targetPerson.setBirthYear(updatedPerson.getBirthYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    @Transactional
    public List<Book> getPersonBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("select b from Book b where b.personId=:i", Book.class);
        query.setParameter("i",id);
        return query.getResultList();
    }
}
