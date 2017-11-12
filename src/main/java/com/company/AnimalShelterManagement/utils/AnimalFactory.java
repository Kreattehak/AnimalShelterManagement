package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
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
        Dog d = newDog(dogName, dogRace);
        d.setAvailableForAdoption(BEFORE_VACCINATION);

        return d;
    }

    public static Dog newGaveUpDog(String dogName, Dog.Race dogRace, LocalDate dateOfBirth,
                                   Person previousOwner, String behaviourDescription) {
        Dog d = newDog(dogName, dogRace);
        d.setDateOfBirth(dateOfBirth);
        d.setPreviousOwner(previousOwner);
        d.setAvailableForAdoption(AVAILABLE);
        d.setBehaviourDescription(behaviourDescription);

        return d;
    }

    public static Dog newDogUnderVeterinaryCare(String dogName, Dog.Race dogRace) {
        Dog d = newDog(dogName, dogRace);
        d.setAvailableForAdoption(UNDER_VETERINARY_CARE);

        return d;
    }

    public static Dog newAvailableForAdoptionDog(String dogName, Dog.Race dogRace) {
        Dog d = newDog(dogName, dogRace);
        d.setAvailableForAdoption(AVAILABLE);

        return d;
    }

    private static Dog newDog(String dogName, Dog.Race dogRace) {
        Dog d = new Dog();
        d.setAnimalType(Animal.Type.DOG);
        d.setAnimalName(dogName);
        d.setDogRace(dogRace);

        return d;
    }

    public static void generateIdentifier(Animal animal) {
        if (animal.getAnimalIdentifier() == null) {
            String firstPart = animal.getAnimalType().getTypeIdentifier();
            String secondPart = String.format("%02d", animal.getDateOfBirth().getYear() % 100);
            String thirdPart = String.format("%04d", animal.getId());
            animal.setAnimalIdentifier(firstPart + secondPart + thirdPart);
        }
    }
}

