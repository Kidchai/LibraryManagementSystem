package kidchai.library.management.controllers;

import jakarta.validation.Valid;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.PeopleService;
import kidchai.library.management.util.person.PersonErrorResponse;
import kidchai.library.management.util.person.PersonNotCreatedException;
import kidchai.library.management.util.person.PersonNotFoundException;
import kidchai.library.management.util.person.PersonNotUpdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public ResponseEntity<List<Person>> index() {
        return ResponseEntity.ok(peopleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> show(@PathVariable("id") int id) {
        return ResponseEntity.ok(peopleService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<Person> create(@RequestBody @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = getExceptionMessage(bindingResult);
            throw new PersonNotCreatedException(errors);
        }
        return ResponseEntity.ok(peopleService.save(person));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> update(@RequestBody @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            String errors = getExceptionMessage(bindingResult);
            throw new PersonNotUpdatedException(errors);
        }
        return ResponseEntity.ok(peopleService.update(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception) {
        PersonErrorResponse error = new PersonErrorResponse(
                "Person with this id not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException exception) {
        PersonErrorResponse error = new PersonErrorResponse(exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotUpdatedException exception) {
        PersonErrorResponse error = new PersonErrorResponse(exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String getExceptionMessage(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(error -> errors
                        .append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("\n"));
        return errors.toString();
    }
}
