package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateAnimalServiceTest {

    @MockBean
    private AnimalRepository animalRepository;
    @MockBean
    private PersonService personService;
    @Autowired
    private AnimalService animalService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPerformReturnAnimals() {
        when(animalRepository.findAll()).thenReturn(new ArrayList<>());

        animalService.returnAnimals();

        verify(animalRepository).findAll();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoption() {
        when(animalRepository.findAnimalByAvailableForAdoption()).thenReturn(new ArrayList<>());

        animalService.returnAnimalsAvailableForAdoption();

        verify(animalRepository).findAnimalByAvailableForAdoption();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsOwnedByPerson() {
        when(animalRepository.findAnimalsOwnedByPerson(anyLong())).thenReturn(new ArrayList<>());

        animalService.returnAnimalsOwnedByPerson(ID_VALUE);

        verify(animalRepository).findAnimalsOwnedByPerson(anyLong());
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnPreviousOwner() {
        when(animalRepository.findPersonByAnimalId(anyLong())).thenReturn(new Person());

        animalService.returnPreviousOwner(ID_VALUE);

        verify(animalRepository).findPersonByAnimalId(anyLong());
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsCount() {
        when(animalRepository.count()).thenReturn(RANDOM_NUMBER);

        animalService.countAnimals();

        verify(animalRepository).count();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsCountForPeople() {
        when(animalRepository.findAnimalsCountForPeople()).thenReturn(new long[]{ID_VALUE, ANOTHER_ID_VALUE});

        animalRepository.findAnimalsCountForPeople();

        verify(animalRepository).findAnimalsCountForPeople();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformDeleteOwnedAnimal() {
        Person testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Dog testDog = new Dog();
        Cat testCat = new Cat();
        testPerson.addAnimal(testDog);
        testPerson.addAnimal(testCat);

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(testDog);

        animalService.deleteOwnedAnimal(ID_VALUE, ID_VALUE);

        assertThat(testPerson.getAnimal(), hasSize(EXPECTED_ANIMALS_FOR_PERSON_COUNT));
        assertFalse(testPerson.getAnimal().contains(testDog));

        verify(personService).returnPerson(anyLong());
        verify(animalRepository).findOne(anyLong());
        verifyNoMoreInteractions(animalRepository);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldThrowExceptionWhenPerformDeleteNotOwnedAnimal() {
        Person testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Dog testDog = new Dog();

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(testDog);

        expectedException.expectMessage("Request could not be processed for PERSON");
        expectedException.expect(ProcessUserRequestException.class);
        animalService.deleteOwnedAnimal(ID_VALUE, ID_VALUE);
    }

    public static Matcher<Animal> checkAnimalFieldsEquality(
            String animalName, Animal.Type animalType, LocalDate dateOfBirth) {
        return allOf(
                hasProperty(NAME, is(animalName)),
                hasProperty(TYPE, is(animalType)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)));
    }
}