package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;
    @Autowired
    private PersonController personController;

    private Person testPerson;

    @Before
    public void setUp() {
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        testPerson.setId(ID_VALUE);
    }

    @Test
    public void shouldPerformReturnPeople() {
        when(personService.returnPeople()).thenReturn(new ArrayList<>());

        personController.returnPeople();

        verify(personService).returnPeople();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformReturnPerson() {
        Person testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);

        personController.returnPerson(ID_VALUE);

        verify(personService).returnPerson(anyLong());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformSavePerson() {
        when(personService.savePerson(any(Person.class))).thenReturn(testPerson);

        personController.savePerson(testPerson);

        verify(personService).savePerson(any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformUpdatePerson() {
        when(personService.updatePerson(any(Person.class))).thenReturn(testPerson);

        personController.updatePerson(testPerson);

        verify(personService).updatePerson(any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformDeletePerson() {
        personController.deletePerson(ID_VALUE);

        verify(personService).deletePerson(ID_VALUE);
        verifyNoMoreInteractions(personService);
    }
}