package kidchai.library.management.services;

import kidchai.library.management.models.Person;
import kidchai.library.management.repositories.PeopleRepository;
import kidchai.library.management.util.person.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    void testFindAll() {
        Person person1 = new Person();
        Person person2 = new Person();
        when(peopleRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<Person> result = peopleService.findAll();

        assertEquals(2, result.size());
        verify(peopleRepository, times(1)).findAll();
    }

    @Test
    void testFindOne() {
        Person person = new Person();
        when(peopleRepository.findById(anyInt())).thenReturn(Optional.of(person));

        Person result = peopleService.findOne(1);

        assertEquals(person, result);
        verify(peopleRepository, times(1)).findById(1);
    }

    @Test
    void testFindOneNotFound() {
        when(peopleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
            peopleService.findOne(1);
        });

        String expectedMessage = "Person with this id not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(peopleRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        Person person = new Person();

        peopleRepository.save(person);

        verify(peopleRepository, times(1)).save(person);
    }

    @Test
    void testUpdate() {
        Person person = new Person();
        person.setId(1);
        when(peopleRepository.save(any(Person.class))).thenReturn(person);

        peopleService.update(1, person);

        verify(peopleRepository, times(1)).save(person);
    }

    @Test
    void testDelete() {
        peopleService.delete(1);
        verify(peopleRepository, times(1)).deleteById(1);
    }
}