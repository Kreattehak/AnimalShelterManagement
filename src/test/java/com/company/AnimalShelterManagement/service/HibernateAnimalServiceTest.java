package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.utils.TestConstant.ANIMAL_NAME;
import static com.company.AnimalShelterManagement.utils.TestConstant.ANIMAL_TYPE;
import static com.company.AnimalShelterManagement.utils.TestConstant.DATE_OF_BIRTH;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class HibernateAnimalServiceTest {

    @Test
    public void dummyTest() {
        //just to get rid of no runnable methods exception
    }

    public static Matcher<Animal> checkAnimalFieldsEquality(
            String animalName, Animal.Type animalType, LocalDate dateOfBirth) {
        return allOf(
                hasProperty(ANIMAL_NAME, is(animalName)),
                hasProperty(ANIMAL_TYPE, is(animalType)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)));
    }
}