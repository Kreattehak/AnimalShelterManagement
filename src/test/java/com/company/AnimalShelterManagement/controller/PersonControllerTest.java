package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.company.AnimalShelterManagement.util.TestConstant.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    private PersonDTO testPersonDTO;

    @MockBean
    private PersonService personService;
    @Autowired
    private PersonController personController;

    @Before
    public void setUp() {
        testPersonDTO = new PersonDTO(PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME);
    }

    @Test
    public void shouldPerformReturnPeople() {
        PersonDTO anotherTestPersonDTO = new PersonDTO(
                PERSON_ID, ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME);

        when(personService.returnPeople()).thenReturn(Arrays.asList(testPersonDTO, anotherTestPersonDTO));

        personController.returnPeople();

        verify(personService).returnPeople();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformReturnPerson() {
        when(personService.returnPerson(anyLong())).thenReturn(testPersonDTO);

        personController.returnPerson(PERSON_ID);

        verify(personService).returnPerson(anyLong());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformSavePerson() {
        when(personService.savePerson(any(PersonDTO.class))).thenReturn(testPersonDTO);

        personController.savePerson(testPersonDTO);

        verify(personService).savePerson(any(PersonDTO.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformUpdatePerson() {
        when(personService.updatePerson(any(PersonDTO.class))).thenReturn(testPersonDTO);

        personController.updatePerson(testPersonDTO);

        verify(personService).updatePerson(any(PersonDTO.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldPerformDeletePerson() {
        personController.deletePerson(PERSON_ID);

        verify(personService).deletePerson(PERSON_ID);
        verifyNoMoreInteractions(personService);
    }
}