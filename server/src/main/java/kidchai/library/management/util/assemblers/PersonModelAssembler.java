package kidchai.library.management.util.assemblers;

import kidchai.library.management.controllers.PeopleController;
import kidchai.library.management.dto.person.PersonDTOForPerson;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<PersonDTOForPerson, EntityModel<PersonDTOForPerson>> {

    @Override
    public EntityModel<PersonDTOForPerson> toModel(PersonDTOForPerson person) {

        return EntityModel.of(person,
                linkTo(methodOn(PeopleController.class).show(person.getId())).withSelfRel(),
                linkTo(methodOn(PeopleController.class).index()).withRel("people"));
    }
}
