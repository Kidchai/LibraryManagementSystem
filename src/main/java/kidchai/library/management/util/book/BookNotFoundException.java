package kidchai.library.management.util.book;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book with this id not found");
    }
}
