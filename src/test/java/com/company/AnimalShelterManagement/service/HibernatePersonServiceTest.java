package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
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

    //TODO: Should I extract it to some class instead of creating this fields in almost every test class?
    private Person testPerson;
    private PersonDTO testPersonDTO;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        testPersonDTO = new PersonDTO(ID_VALUE, PERSON_FIRST_NAME, PERSON_LAST_NAME);
    }

    @Test
    public void shouldPerformReturnPeople() {
        Person anotherTestPerson = new Person(ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME);
        when(personRepository.findAll()).thenReturn(Arrays.asList(testPerson, anotherTestPerson));

        hibernatePersonService.returnPeople();

        verify(personRepository).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldPerformReturnPerson() {
        when(personRepository.findOne(anyLong())).thenReturn(testPerson);

        hibernatePersonService.returnPerson(ID_VALUE);

        verify(personRepository).findOne(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldThrowExceptionWhenPersonIdWasNotFound() {
        when(personRepository.findOne(anyLong())).thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("was not found");
        hibernatePersonService.returnPerson(ID_VALUE);
    }

    @Test
    public void shouldPerformSavePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        hibernatePersonService.savePerson(testPersonDTO);

        verify(personRepository).save(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    public void shouldPerformUpdatePerson() {
        PersonDTO anotherPersonDTO = new PersonDTO(
                ID_VALUE, ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME);

        when(personRepository.findOne(anyLong())).thenReturn(testPerson);
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        hibernatePersonService.updatePerson(anotherPersonDTO);

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

        hibernatePersonService.deletePerson(ID_VALUE);

        verify(personRepository).findOne(anyLong());
        verify(personRepository).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    public static Matcher<PersonDTO> checkPersonDtoFieldsEquality(String firstName, String lastName) {
        return allOf(
                hasProperty(FIRST_NAME, is(firstName)),
                hasProperty(LAST_NAME, is(lastName)));
    }

    public static Matcher<Person> checkPersonFieldsEquality(String firstName, String lastName) {
        return allOf(
                hasProperty(FIRST_NAME, is(firstName)),
                hasProperty(LAST_NAME, is(lastName)));
    }
}