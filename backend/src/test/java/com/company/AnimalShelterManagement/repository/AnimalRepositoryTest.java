package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest.checkAnimalFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newAvailableForAdoptionDog;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newlyReceivedDog;
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

    private Dog dog;

    @Test
    public void shouldReturnAllDogsAvailableForAdoption() {
        createAndPersistTwoDogs();

        Iterable<Animal> animals = animalRepository.findAnimalByAvailableForAdoption();

        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, Animal.Type.DOG, LocalDate.now())));
        assertThat(animals, not(hasItem(checkAnimalFieldsEquality(
                ANOTHER_DOG_NAME, Animal.Type.DOG, LocalDate.now()))));
    }

    @Test
    public void shouldReturnPersonRelatedWithAnimal() {
        setAsPreviousOwnerAndPersistPerson();

        Person p = animalRepository.findPersonByAnimalId(dog.getId());

        assertThat(p.getAnimal(), hasItem(dog));
        assertThat(dog.getPreviousOwner(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldReturnAnimalsCountForPeople() {
        setAsPreviousOwnerAndPersistPerson();

        long[] count = animalRepository.findAnimalsCountForPeople();

        assertEquals(EXPECTED_ANIMALS_FOR_PERSON_COUNT, count[count.length - 1]);
    }

    private void createAndPersistTwoDogs() {
        dog = newAvailableForAdoptionDog(DOG_NAME, Dog.Race.GERMAN_SHEPERD);
        dog.setDateOfBirth(LocalDate.now());
        Dog anotherDog = newlyReceivedDog(ANOTHER_DOG_NAME, Dog.Race.CROSSBREAD);
        anotherDog.setDateOfBirth(LocalDate.now());

        saveTwoDogsInDatabase(dog, anotherDog);
    }

    private void saveTwoDogsInDatabase(Dog dog, Dog anotherDog) {
        entityManager.persist(dog);
        entityManager.persist(anotherDog);
    }

    private void setAsPreviousOwnerAndPersistPerson() {
        createAndPersistTwoDogs();
        Person person = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        entityManager.persist(person);
        person.addAnimal(dog);
    }
}