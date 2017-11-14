package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.utils.AnimalFactory.*;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateAnimalServiceTest {

    @MockBean
    private AnimalRepository animalRepository;
    @Autowired
    private HibernateAnimalService hibernateAnimalService;

    private Animal testAnimal;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testAnimal = newAvailableForAdoptionDog(DOG_NAME, Dog.Race.HUSKY);
    }

    @Test
    public void shouldPerformReturnAnimals() {
        Animal anotherTestAnimal = newAvailableForAdoptionCat(CAT_NAME, Cat.Race.PERSIAN);
        when(animalRepository.findAll()).thenReturn(asList(testAnimal, anotherTestAnimal));

        hibernateAnimalService.returnAnimals();

        verify(animalRepository).findAll();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoption() {
        Animal anotherTestAnimal = newAvailableForAdoptionCat(CAT_NAME, Cat.Race.PERSIAN);
        when(animalRepository.findAnimalByAvailableForAdoption())
                .thenReturn(asList(testAnimal, anotherTestAnimal));

        hibernateAnimalService.returnAnimalsAvailableForAdoption();

        verify(animalRepository).findAnimalByAvailableForAdoption();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsCount() {
        when(animalRepository.count()).thenReturn(RANDOM_NUMBER);

        hibernateAnimalService.countAnimals();

        verify(animalRepository).count();
        verifyNoMoreInteractions(animalRepository);
    }

    public static Matcher<Animal> checkAnimalFieldsEquality(
            String animalName, Animal.Type animalType, LocalDate dateOfBirth) {
        return allOf(
                hasProperty(ANIMAL_NAME, is(animalName)),
                hasProperty(ANIMAL_TYPE, is(animalType)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)));
    }
}