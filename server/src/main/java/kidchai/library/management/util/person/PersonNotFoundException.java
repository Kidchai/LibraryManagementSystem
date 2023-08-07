package kidchai.library.management.util.person;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person with this id not found");
    }
}
