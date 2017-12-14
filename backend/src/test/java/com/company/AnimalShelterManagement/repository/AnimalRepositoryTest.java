package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest.checkAnimalFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newAvailableForAdoptionDog;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newlyReceivedDog;
import static com.company.AnimalShelterManagement.utils.SearchForAnimalParams.DEFAULT_PAGE_SIZE;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
public class AnimalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AnimalRepository animalRepository;

    private Dog testDog;
    private Person testPerson;

    @Test
    public void shouldReturnAllAnimalsAvailableForAdoptionWithNoDataProvided() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Iterable<Animal> animals = animalRepository.findAnimalsByAvailableForAdoption(pageable);

        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
        assertThat(animals, not(hasItem(checkAnimalFieldsEquality(ANOTHER_DOG_NAME, DOG, LocalDate.now()))));
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptionByName() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Iterable<Animal> animals = animalRepository.findAnimalsAvailableForAdoptionByName(DOG, DOG_NAME, pageable);

        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptionByIdentifier() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        AnimalFactory.generateAnimalIdentifier(testDog);

        Iterable<Animal> animals = animalRepository.findAnimalsAvailableForAdoptionByIdentifier(DOG,
                testDog.getAnimalIdentifier(), pageable);

        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldPerformReturnNotAdoptedAnimalsWithNoDataProvided() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Page<Animal> animals = animalRepository.findNotAdoptedAnimals(pageable);

        assertEquals(EXPECTED_NOT_ADOPTED_ANIMALS_COUNT, animals.getNumberOfElements());
        assertThat(animals, allOf(
                hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)),
                hasItem(checkAnimalFieldsEquality(ANOTHER_DOG_NAME, DOG, LocalDate.now()))
        ));
    }

    @Test
    public void shouldPerformReturnNotAdoptedAnimalsByName() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Page<Animal> animals = animalRepository.findNotAdoptedAnimalsByName(DOG, DOG_NAME, pageable);

        assertEquals(ONE_ENTITY, animals.getNumberOfElements());
        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldPerformReturnNotAdoptedAnimalsByIdentifier() {
        createAndPersistTwoDogs();
        AnimalFactory.generateAnimalIdentifier(testDog);
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Page<Animal> animals = animalRepository.findNotAdoptedAnimalsByIdentifier(DOG,
                testDog.getAnimalIdentifier(), pageable);

        assertEquals(ONE_ENTITY, animals.getNumberOfElements());
        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldPerformReturnPagedNotAdoptedAnimals() {
        Pageable pageable = new PageRequest(FIRST_PAGE, EXPECTED_ANIMALS_IN_SHELTER_COUNT);

        Page<Animal> animals = animalRepository.findNotAdoptedAnimals(pageable);

        assertEquals(EXPECTED_ANIMALS_IN_SHELTER_COUNT, animals.getNumberOfElements());
    }

    @Test
    public void shouldReturnAnimalsWithLongestWaitingTime() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Page<Animal> animalsWithLongestWaitingTime = animalRepository.findAnimalsWithLongestWaitingTime(pageable);

        assertEquals(EXPECTED_WITH_LONGEST_WAITING_TIME_ANIMALS_COUNT, animalsWithLongestWaitingTime.getNumberOfElements());
        assertThat(animalsWithLongestWaitingTime, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldReturnPagedAnimalsWithLongestWaitingTime() {
        Pageable pageable = new PageRequest(FIRST_PAGE, ONE_ENTITY);

        Page<Animal> animalsWithLongestWaitingTime = animalRepository.findAnimalsWithLongestWaitingTime(pageable);

        assertEquals(ONE_ENTITY, animalsWithLongestWaitingTime.getNumberOfElements());
    }

    @Test
    public void shouldReturnRecentlyAddedAnimals() {
        createAndPersistTwoDogs();
        Pageable pageable = new PageRequest(FIRST_PAGE, DEFAULT_PAGE_SIZE);

        Page<Animal> recentlyAddedAnimals = animalRepository.findRecentlyAddedAnimals(pageable);

        assertEquals(EXPECTED_NOT_ADOPTED_ANIMALS_COUNT, recentlyAddedAnimals.getNumberOfElements());
        assertThat(recentlyAddedAnimals, hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldReturnPagedRecentlyAddedAnimals() {
        Pageable pageable = new PageRequest(FIRST_PAGE, ONE_ENTITY);

        Page<Animal> recentlyAddedAnimals = animalRepository.findRecentlyAddedAnimals(pageable);

        assertEquals(ONE_ENTITY, recentlyAddedAnimals.getNumberOfElements());
    }

    @Test
    public void shouldReturnAnimalsOwnedByPerson() {
        setAsPreviousOwnerAndPersistPerson();

        Iterable<Animal> animalsOwnedByPerson = animalRepository.findAnimalsOwnedByPerson(testPerson.getId());

        assertThat(animalsOwnedByPerson, hasItem(checkAnimalFieldsEquality(
                DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }

    @Test
    public void shouldReturnPersonRelatedWithAnimal() {
        setAsPreviousOwnerAndPersistPerson();

        Person p = animalRepository.findPersonByAnimalId(testDog.getId());

        assertThat(p.getAnimal(), hasItem(testDog));
        assertThat(testDog.getPreviousOwner(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldReturnAnimalsCountForPeople() {
        long[] count = animalRepository.findAnimalsCountForPeople();

        int animalsCountForOnlyPerson = (int) count[0];

        assertEquals(EXPECTED_ANIMALS_FOR_PERSON_COUNT, count.length);
        assertEquals(EXPECTED_ANIMALS_FOR_PERSON_COUNT, animalsCountForOnlyPerson);
    }

    private void createAndPersistTwoDogs() {
        testDog = newAvailableForAdoptionDog(DOG_NAME, Dog.Race.GERMAN_SHEPERD);
        testDog.setDateOfBirth(DATE_OF_BIRTH_VALUE);
        Dog anotherDog = newlyReceivedDog(ANOTHER_DOG_NAME, Dog.Race.CROSSBREAD);
        anotherDog.setDateOfBirth(LocalDate.now());

        saveTwoDogsInDatabase(testDog, anotherDog);
    }

    private void saveTwoDogsInDatabase(Dog dog, Dog anotherDog) {
        entityManager.persist(dog);
        entityManager.persist(anotherDog);
    }

    private void setAsPreviousOwnerAndPersistPerson() {
        createAndPersistTwoDogs();
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        entityManager.persist(testPerson);
        testPerson.addAnimal(testDog);
    }
}