package kidchai.util;

import kidchai.dao.PersonDao;
import kidchai.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (false) {
            errors.rejectValue("name", "", "This user already registered");
        }
    }

//    @Override
//    public void validate(Object target, Errors errors) {
//        Person person = (Person) target;
//        if (personDao.show(person.getId(), person.getName(), person.getBirthYear()) != null) {
//            errors.rejectValue("name", "", "This user already registered");
//        }
//    }
}
