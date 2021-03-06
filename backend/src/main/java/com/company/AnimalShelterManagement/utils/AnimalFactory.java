package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.BEFORE_VACCINATION;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.UNDER_VETERINARY_CARE;

public class AnimalFactory {

    private AnimalFactory() {
    }

    public static Dog newlyReceivedDog(String dogName, Dog.Race dogRace) {
        Dog d = createNewDog(dogName, dogRace);
        d.setAvailableForAdoption(BEFORE_VACCINATION);

        return d;
    }

    public static Cat newlyReceivedCat(String catName, Cat.Race catRace) {
        Cat c = createNewCat(catName, catRace);
        c.setAvailableForAdoption(BEFORE_VACCINATION);

        return c;
    }

    public static Dog newGaveUpDog(String dogName, Dog.Race dogRace, LocalDate dateOfBirth,
                                   Person previousOwner, String behaviorDescription) {
        Dog d = createNewDog(dogName, dogRace);
        d.setDateOfBirth(dateOfBirth);
        d.setPreviousOwner(previousOwner);
        d.setAvailableForAdoption(AVAILABLE);
        d.setBehaviorDescription(behaviorDescription);

        return d;
    }

    public static Dog newDogUnderVeterinaryCare(String dogName, Dog.Race dogRace) {
        Dog d = createNewDog(dogName, dogRace);
        d.setAvailableForAdoption(UNDER_VETERINARY_CARE);

        return d;
    }

    public static Dog newAvailableForAdoptionDog(String dogName, Dog.Race dogRace) {
        Dog d = createNewDog(dogName, dogRace);
        d.setAvailableForAdoption(AVAILABLE);

        return d;
    }

    public static Cat newAvailableForAdoptionCat(String catName, Cat.Race catRace) {
        Cat c = createNewCat(catName, catRace);
        c.setAvailableForAdoption(AVAILABLE);

        return c;
    }

    public static void generateAnimalIdentifier(Animal animal) {
        if (animal.getAnimalIdentifier() == null) {
            String firstPart = animal.getType().getTypeIdentifier();
            String secondPart = String.format("%02d", animal.getDateOfBirth().getYear() % 100);
            String thirdPart = String.format("%04d", animal.getId());
            animal.setAnimalIdentifier(firstPart + secondPart + thirdPart);
        }
    }

    private static Dog createNewDog(String dogName, Dog.Race dogRace) {
        Dog d = new Dog();
        d.setType(Animal.Type.DOG);
        d.setName(dogName);
        d.setSubType(dogRace);

        return d;
    }

    private static Cat createNewCat(String dogName, Cat.Race catRace) {
        Cat c = new Cat();
        c.setType(Animal.Type.CAT);
        c.setName(dogName);
        c.setSubType(catRace);

        return c;
    }
}

