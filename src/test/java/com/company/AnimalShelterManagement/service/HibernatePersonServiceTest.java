package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.company.AnimalShelterManagement.util.TestConstant.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernatePersonServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @Autowired
    private HibernatePersonService hibernatePersonService;

    private Person testPerson;
    private PersonDTO testPersonDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testPerson = new Person("Test", "Person");
        testPersonDTO = new PersonDTO(PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME);
    }

    @After
    public void tearDown() {
        personRepository = null;
        testPerson = null;
    }

    @Test
    public void shouldReturnPerson() {
        when(personRepository.findOne(anyLong())).thenReturn(testPerson);

        hibernatePersonService.returnPerson(PERSON_ID);

        verify(personRepository).findOne(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldDeletePerson() {
        when(personRepository.findOne(anyLong())).thenReturn(testPerson);

        hibernatePersonService.deletePerson(PERSON_ID);

        verify(personRepository).findOne(anyLong());
        verify(personRepository).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    public static Matcher<PersonDTO> checkPersonFieldsEquality(String firstName, String lastName) {
        return allOf(
                hasProperty(FIRST_NAME, is(firstName)),
                hasProperty(LAST_NAME, is(lastName)));
    }

}