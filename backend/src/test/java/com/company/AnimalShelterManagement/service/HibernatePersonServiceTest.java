package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private PersonService personService;

    //TODO: Should I extract it to some class instead of creating this fields in almost every test class?
    private Person testPerson;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
    }

    @Test
    public void shouldPerformReturnPeople() {
        when(personRepository.findAll()).thenReturn(new ArrayList<>());

        personService.returnPeople();

        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldPerformReturnPerson() {
        when(personRepository.findOne(anyLong())).thenReturn(testPerson);

        personService.returnPerson(ID_VALUE);

        verify(personRepository).findOne(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldThrowExceptionWhenPersonIdWasNotFound() {
        when(personRepository.findOne(anyLong())).thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("was not found");
        personService.returnPerson(ID_VALUE);
    }

    @Test
    public void shouldPerformSavePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        personService.savePerson(testPerson);

        verify(personRepository).save(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldPerformUpdatePerson() {
        Person anotherPerson = new Person(ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME);
        anotherPerson.setId(ID_VALUE);

        when(personRepository.findOne(anyLong())).thenReturn(testPerson);
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        personService.updatePerson(anotherPerson);

        //updatePerson() operates on reference returned by findOne()
        assertThat(testPerson, is(checkPersonFieldsEquality(
                ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME)));

        verify(personRepository).save(any(Person.class));
        verify(personRepository).findOne(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldPerformDeletePerson() {
        when(personRepository.findOne(anyLong())).thenReturn(testPerson);

        personService.deletePerson(ID_VALUE);

        verify(personRepository).findOne(anyLong());
        verify(personRepository).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    public static Matcher<Person> checkPersonFieldsEquality(String firstName, String lastName) {
        return allOf(
                hasProperty(FIRST_NAME, is(firstName)),
                hasProperty(LAST_NAME, is(lastName)));
    }
}