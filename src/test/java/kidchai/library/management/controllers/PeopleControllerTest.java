package kidchai.library.management.controllers;

import kidchai.library.management.models.Person;
import kidchai.library.management.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeopleController.class)
public class PeopleControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @Autowired
    public PeopleControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testIndex() throws Exception {
        List<Person> people = new ArrayList<>();
        when(peopleService.findAll()).thenReturn(people);

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/index"))
                .andExpect(model().attribute("people", people));
    }

    @Test
    void testNewPerson() throws Exception {
        mockMvc.perform(get("/people/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/new"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    void testCreate() throws Exception {
        Person person = new Person();
        person.setName("John Doe");

        mockMvc.perform(post("/people")
                        .flashAttr("person", person))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/people"));

        verify(peopleService, times(1)).save(person);
    }

    @Test
    void testEdit() throws Exception {
        int personId = 1;
        Person person = new Person();
        person.setId(personId);
        when(peopleService.findOne(personId)).thenReturn(person);

        mockMvc.perform(get("/people/" + personId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("people/edit"))
                .andExpect(model().attribute("person", person));
    }

    @Test
    void testUpdate() throws Exception {
        int personId = 1;
        Person person = new Person();
        person.setId(personId);
        person.setName("John Doe");

        mockMvc.perform(patch("/people/" + personId)
                        .flashAttr("person", person))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/people"));

        verify(peopleService, times(1)).update(personId, person);
    }

    @Test
    void testDelete() throws Exception {
        int personId = 1;

        mockMvc.perform(delete("/people/" + personId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/people"));

        verify(peopleService, times(1)).delete(personId);
    }
}
