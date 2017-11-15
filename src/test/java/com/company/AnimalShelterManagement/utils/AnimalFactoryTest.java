package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
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

        checkEqualityOfCommonDogFields(d, CROSSBREAD, BEFORE_VACCINATION);
    }

    @Test
    public void shouldCreateNewGaveUpDog() {
        Person p = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Dog d = newGaveUpDog(DOG_NAME, HUSKY, LocalDate.now(), p, ANIMAL_BEHAVIOUR_DESCRIPTION);

        checkEqualityOfCommonDogFields(d, HUSKY, AVAILABLE);
        assertEquals(d.getDateOfBirth(), LocalDate.now());
        assertEquals(d.getPreviousOwner(), p);
        assertEquals(d.getBehaviourDescription(), ANIMAL_BEHAVIOUR_DESCRIPTION);
    }

    @Test
    public void shouldCreateNewDogUnderVeterinaryCare() {
        Dog d = newDogUnderVeterinaryCare(DOG_NAME, GERMAN_SHEPERD);

        checkEqualityOfCommonDogFields(d, GERMAN_SHEPERD, UNDER_VETERINARY_CARE);
    }

    @Test
    public void shouldCreateNewAvailableForAdoptionDog() {
        Dog d = newAvailableForAdoptionDog(DOG_NAME, ENGLISH_COCKER_SPANIEL);

        checkEqualityOfCommonDogFields(d, ENGLISH_COCKER_SPANIEL, AVAILABLE);
    }

    @Test
    public void shouldGenerateAnimalIdentifier() {
        Dog d = newlyReceivedDog(DOG_NAME, CROSSBREAD);
        d.setDateOfBirth(LocalDate.now());
        d.setId(ID_VALUE);

        generateAnimalIdentifier(d);

        assertEquals(d.getAnimalIdentifier(), ANIMAL_IDENTIFIER_PATTERN);
    }

    private void checkEqualityOfCommonDogFields(Dog dog, Dog.Race dogRace,
                                                Animal.AvailableForAdoption availability) {
        assertEquals(dog.getAnimalType(), DOG);
        assertEquals(dog.getAnimalName(), DOG_NAME);
        assertEquals(dog.getDogRace(), dogRace);
        assertEquals(dog.getAvailableForAdoption(), availability);
    }
}