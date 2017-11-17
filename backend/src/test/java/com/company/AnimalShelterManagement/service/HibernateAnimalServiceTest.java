package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateAnimalServiceTest {

    @MockBean
    private AnimalRepository animalRepository;
    @Autowired
    private HibernateAnimalService hibernateAnimalService;

    @Test
    public void shouldPerformReturnAnimals() {
        when(animalRepository.findAll()).thenReturn(new ArrayList<>());

        hibernateAnimalService.returnAnimals();

        verify(animalRepository).findAll();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoption() {
        when(animalRepository.findAnimalByAvailableForAdoption()).thenReturn(new ArrayList<>());

        hibernateAnimalService.returnAnimalsAvailableForAdoption();

        verify(animalRepository).findAnimalByAvailableForAdoption();
        verifyNoMoreInteractions(animalRepository);
    }

    @Test
    public void shouldPerformReturnPreviousOwner() {
        when(animalRepository.findPersonByAnimalId(anyLong())).thenReturn(new Person());

        hibernateAnimalService.returnPreviousOwner(ID_VALUE);

        verify(animalRepository).findPersonByAnimalId(anyLong());
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
                hasProperty(NAME, is(animalName)),
                hasProperty(TYPE, is(animalType)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)));
    }
}