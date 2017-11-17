package com.company.AnimalShelterManagement.model.dto;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption;

import java.time.LocalDate;

public abstract class AnimalDTO extends BaseDTO {

    private String name;
    private Animal.Type type;
    private String behaviourDescription;
    private AvailableForAdoption availableForAdoption;
    private String animalIdentifier;
    private LocalDate dateOfBirth;

    AnimalDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animal.Type getType() {
        return type;
    }

    public void setType(Animal.Type type) {
        this.type = type;
    }

    public String getBehaviourDescription() {
        return behaviourDescription;
    }

    public void setBehaviourDescription(String behaviourDescription) {
        this.behaviourDescription = behaviourDescription;
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
