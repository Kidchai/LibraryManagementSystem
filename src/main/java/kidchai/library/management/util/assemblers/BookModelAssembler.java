package kidchai.library.management.util.assemblers;

import kidchai.library.management.controllers.BooksController;
import kidchai.library.management.dto.book.BookDTOForBook;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<BookDTOForBook, EntityModel<BookDTOForBook>> {

    @Override
    public EntityModel<BookDTOForBook> toModel(BookDTOForBook book) {

        return EntityModel.of(book,
                linkTo(methodOn(BooksController.class).show(book.getId())).withSelfRel(),
                linkTo(methodOn(BooksController.class).index(1, "", 15, false)).withRel("books"));
    }
}
