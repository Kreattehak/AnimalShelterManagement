package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import org.junit.Test;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.*;
import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.model.Dog.Race.*;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.*;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.junit.Assert.assertEquals;

public class AnimalFactoryTest {

    @Test
    public void shouldCreateNewlyReceivedDog() {
        Dog d = newlyReceivedDog(DOG_NAME, CROSSBREAD);

        assertEquals(d.getAnimalType(), DOG);
        assertEquals(d.getAnimalName(), DOG_NAME);
        assertEquals(d.getDogRace(), CROSSBREAD);
        assertEquals(d.getAvailableForAdoption(), BEFORE_VACCINATION);
    }

    @Test
    public void shouldCreateNewGaveUpDog() {
        Person p = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Dog d = newGaveUpDog(DOG_NAME, HUSKY, LocalDate.now(), p, ANIMAL_BEHAVIOUR_DESCRIPTION);

        assertEquals(d.getAnimalType(), DOG);
        assertEquals(d.getAnimalName(), DOG_NAME);
        assertEquals(d.getDogRace(), HUSKY);
        assertEquals(d.getDateOfBirth(), LocalDate.now());
        assertEquals(d.getPreviousOwner(), p);
        assertEquals(d.getAvailableForAdoption(), AVAILABLE);
        assertEquals(d.getBehaviourDescription(), ANIMAL_BEHAVIOUR_DESCRIPTION);
    }

    @Test
    public void shouldCreateNewDogUnderVeterinaryCare() {
        Dog d = newDogUnderVeterinaryCare(DOG_NAME, GERMAN_SHEPERD);

        assertEquals(d.getAnimalType(), DOG);
        assertEquals(d.getAnimalName(), DOG_NAME);
        assertEquals(d.getDogRace(), GERMAN_SHEPERD);
        assertEquals(d.getAvailableForAdoption(), UNDER_VETERINARY_CARE);
    }

    @Test
    public void shouldCreateNewAvailableForAdoptionDog() {
        Dog d = newAvailableForAdoptionDog(DOG_NAME, ENGLISH_COCKER_SPANIEL);

        assertEquals(d.getAnimalType(), DOG);
        assertEquals(d.getAnimalName(), DOG_NAME);
        assertEquals(d.getDogRace(), ENGLISH_COCKER_SPANIEL);
        assertEquals(d.getAvailableForAdoption(), AVAILABLE);
    }

    @Test
    public void shouldGenerateAnimalIdentifier() {
        Dog d = newlyReceivedDog(DOG_NAME, CROSSBREAD);
        d.setDateOfBirth(LocalDate.now());
        d.setId(ID_VALUE);

        generateAnimalIdentifier(d);

        assertEquals(d.getAnimalIdentifier(), ANIMAL_IDENTIFIER_PATTERN);
    }
}