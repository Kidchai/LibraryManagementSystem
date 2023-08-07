package kidchai.library.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kidchai.library.management.models.Book;
import kidchai.library.management.models.Person;
import kidchai.library.management.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;


    @MockBean
    private PeopleService peopleService;

    @Autowired
    public PeopleControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void testIndex() throws Exception {
        List<Person> people = new ArrayList<>();
        when(peopleService.findAll()).thenReturn(people);

        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk());
    }

    @Test
    void testShow() throws Exception {
        int personId = 1;
        Person expectedPerson = new Person();
        expectedPerson.setId(1);
        expectedPerson.setBooks(Collections.singletonList(new Book()));

        when(peopleService.findOne(personId)).thenReturn(expectedPerson);

        mockMvc.perform(get("/api/people/" + personId))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        Person newPerson = new Person();
        newPerson.setName("John Doe");

        mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isOk());

        verify(peopleService, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdate() throws Exception {
        int personId = 1;
        Person person = new Person();
        person.setId(personId);
        person.setName("John Doe");

        mockMvc.perform(patch("/api/people/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(peopleService, times(1)).update(eq(personId), any(Person.class));
    }

    @Test
    void testDelete() throws Exception {
        int personId = 1;

        mockMvc.perform(delete("/api/people/" + personId)
                        .flashAttr("personId", personId))
                .andExpect(status().isOk());

        verify(peopleService, times(1)).delete(personId);
    }
}
