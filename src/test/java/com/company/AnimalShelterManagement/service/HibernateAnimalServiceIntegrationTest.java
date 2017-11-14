package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.company.AnimalShelterManagement.model.Animal.Type.CAT;
import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest.checkAnimalFieldsEquality;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newAvailableForAdoptionCat;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newlyReceivedDog;
import static com.company.AnimalShelterManagement.utils.TestConstant.CAT_NAME;
import static com.company.AnimalShelterManagement.utils.TestConstant.DOG_NAME;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateAnimalServiceIntegrationTest {

    private static long TWO_ANIMALS = 2;

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private HibernateAnimalService hibernateAnimalService;

    private Animal testAnimal;
    private Animal anotherTestAnimal;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testAnimal = newlyReceivedDog(DOG_NAME, Dog.Race.HUSKY);
        anotherTestAnimal = newAvailableForAdoptionCat(CAT_NAME, Cat.Race.PERSIAN);
    }

    @Test
    public void shouldReturnAnimals() {
        setUpTwoAnimalsInDatabase();

        Iterable<Animal> animals = hibernateAnimalService.returnAnimals();

        assertThat(animals, allOf(
                hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, null)),
                hasItem(checkAnimalFieldsEquality(CAT_NAME, CAT, null))));
    }

    @Test
    public void shouldReturnAnimalsAvailableForAdoption_OnlyOneIsAvailable() {
        setUpTwoAnimalsInDatabase();

        Iterable<Animal> animals = hibernateAnimalService.returnAnimalsAvailableForAdoption();

        assertThat(animals, allOf(
                not(hasItem(checkAnimalFieldsEquality(DOG_NAME, DOG, null))),
                hasItem(checkAnimalFieldsEquality(CAT_NAME, CAT, null))));
    }

    @Test
    public void shouldReturnAnimalsCount() {
        long countBeforeAddAnimals = hibernateAnimalService.countAnimals();
        setUpTwoAnimalsInDatabase();

        long count = hibernateAnimalService.countAnimals();

        assertEquals(count, countBeforeAddAnimals + TWO_ANIMALS);
    }

    private void setUpTwoAnimalsInDatabase() {
        animalRepository.save(testAnimal);
        animalRepository.save(anotherTestAnimal);
    }
}