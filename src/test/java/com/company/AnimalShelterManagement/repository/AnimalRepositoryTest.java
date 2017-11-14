package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Dog;
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
import static com.company.AnimalShelterManagement.utils.TestConstant.ANOTHER_DOG_NAME;
import static com.company.AnimalShelterManagement.utils.TestConstant.DOG_NAME;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
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

    @Test
    public void shouldReturnAllDogsAvailableForAdoption() {
        Dog dog = newAvailableForAdoptionDog(DOG_NAME, Dog.Race.GERMAN_SHEPERD);
        dog.setDateOfBirth(LocalDate.now());
        Dog anotherDog = newlyReceivedDog(ANOTHER_DOG_NAME, Dog.Race.CROSSBREAD);
        anotherDog.setDateOfBirth(LocalDate.now());

        this.entityManager.persist(dog);
        this.entityManager.persist(anotherDog);

        Iterable<Animal> animals = animalRepository.findAnimalByAvailableForAdoption();

        assertThat(animals, hasItem(checkAnimalFieldsEquality(DOG_NAME, Animal.Type.DOG, LocalDate.now())));
        assertThat(animals, not(hasItem(checkAnimalFieldsEquality(
                ANOTHER_DOG_NAME, Animal.Type.DOG, LocalDate.now()))));
    }
}