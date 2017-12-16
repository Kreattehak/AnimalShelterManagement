package com.company.AnimalShelterManagement.model.dto;

import com.company.AnimalShelterManagement.model.Animal;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public abstract class AnimalDTO extends BaseDTO {

    private String name;
    private Animal.Type type;
    private String behaviorDescription;
    private String animalIdentifier;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfRegistration;


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

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
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

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }
}
