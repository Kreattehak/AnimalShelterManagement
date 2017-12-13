package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import com.company.AnimalShelterManagement.utils.RestResponsePage;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.ADOPTED;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
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

    private Person testPerson;
    private Dog testDog;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        testDog = new Dog();
        testDog.setId(ID_VALUE);
        testDog.setAvailableForAdoption(AVAILABLE);
    }

    @Test
    public void shouldPerformReturnAnimals() {
        when(animalRepository.findAll()).thenReturn(new ArrayList<>());

        animalService.returnAnimals();

        verify(animalRepository).findAll();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptionWithNoDataProvided() {
        when(animalRepository.findAnimalsByAvailableForAdoption()).thenReturn(new ArrayList<>());

        animalService.returnAnimalsAvailableForAdoption(null, null, null);

        verify(animalRepository).findAnimalsByAvailableForAdoption();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptionByName() {
        when(animalRepository.findAnimalsAvailableForAdoptionByName(any(Animal.Type.class), anyString()))
                .thenReturn(new ArrayList<>());

        animalService.returnAnimalsAvailableForAdoption(DOG, null, DOG_NAME);

        verify(animalRepository).findAnimalsAvailableForAdoptionByName(any(Animal.Type.class), anyString());
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptionByIdentifier() {
        when(animalRepository.findAnimalsAvailableForAdoptionByIdentifier(any(Animal.Type.class), anyString()))
                .thenReturn(new ArrayList<>());

        animalService.returnAnimalsAvailableForAdoption(DOG, ANIMAL_IDENTIFIER_PATTERN, null);

        verify(animalRepository).findAnimalsAvailableForAdoptionByIdentifier(any(Animal.Type.class), anyString());
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsWithLongestWaitingTime() {
        when(animalRepository.findAnimalsWithLongestWaitingTime(any(Pageable.class))).thenReturn(new RestResponsePage<>());

        animalService.returnAnimalsWithLongestWaitingTime(FIRST_PAGE, PAGE_SIZE_VALUE);

        verify(animalRepository).findAnimalsWithLongestWaitingTime(any(Pageable.class));
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnRecentlyAddedAnimals() {
        when(animalRepository.findRecentlyAddedAnimals(any(Pageable.class))).thenReturn(new RestResponsePage<>());

        animalService.returnRecentlyAddedAnimals(FIRST_PAGE, PAGE_SIZE_VALUE);

        verify(animalRepository).findRecentlyAddedAnimals(any(Pageable.class));
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
    public void shouldPerformAddAnimalToPerson() {
        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(testDog);

        animalService.addAnimalToPerson(ID_VALUE, ID_VALUE);

        assertThat(testPerson.getAnimal(), hasSize(EXPECTED_ANIMALS_FOR_PERSON_COUNT));
        assertTrue(testPerson.getAnimal().contains(testDog));
        assertEquals(ADOPTED, testDog.getAvailableForAdoption());
    }

    @Test
    public void shouldThrowExceptionWhenAnimalIsNotAvailable() {
        testDog.setAvailableForAdoption(ADOPTED);

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(testDog);

        expectedException.expectMessage("Request could not be processed for ANIMAL");
        expectedException.expect(ProcessUserRequestException.class);
        animalService.addAnimalToPerson(ID_VALUE, ID_VALUE);
    }

    @Test
    public void shouldThrowExceptionWhenAddTheSameAnimalToPerson() {
        testPerson.addAnimal(testDog);
        testDog.setAvailableForAdoption(AVAILABLE);

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(testDog);

        expectedException.expectMessage("Request could not be processed for PERSON");
        expectedException.expect(ProcessUserRequestException.class);
        animalService.addAnimalToPerson(ID_VALUE, ID_VALUE);
    }

    @Test
    public void shouldPerformDeleteOwnedAnimal() {
        addAnimalsToPerson();

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
        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(animalRepository.findOne(anyLong())).thenReturn(new Dog());

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

    private void addAnimalsToPerson() {
        Cat testCat = new Cat();
        testPerson.addAnimal(testDog);
        testPerson.addAnimal(testCat);
    }
}