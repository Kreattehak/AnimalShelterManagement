package com.company.AnimalShelterManagement.model.dto;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption;

import java.time.LocalDate;

public class AnimalDTO extends BaseDTO {
    private String animalName;
    private Animal.Type animalType;
    private AvailableForAdoption availableForAdoption;
    private String animalIdentifier;
    private LocalDate dateOfBirth;

    public AnimalDTO() {
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Animal.Type getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Animal.Type animalType) {
        this.animalType = animalType;
    }

    public AvailableForAdoption getAvailableForAdoption() {
        return availableForAdoption;
    }

    public void setAvailableForAdoption(AvailableForAdoption availableForAdoption) {
        this.availableForAdoption = availableForAdoption;
    }

    public String getAnimalIdentifier() {
        return animalIdentifier;
    }

    public void setAnimalIdentifier(String animalIdentifier) {
        this.animalIdentifier = animalIdentifier;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
