package com.company.AnimalShelterManagement.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "animal")
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal extends BaseEntity {

    @Column(name = "animal_name", nullable = false, length = 25)
    @Length(min = 3, message = "{validation.minLength}")
    @NotNull
    private String animalName;

    @Column(name = "animal_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Type animalType;

    @Column(name = "behaviour_description", length = 500)
    @Length(min = 3, message = "{validation.minLength}")
    private String behaviourDescription;

    @Column(name = "available_for_adoption", nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AvailableForAdoption availableForAdoption;

    @Column(name = "animal_identifier", length = 8)
    @Pattern(regexp = "\\d{8}", message = "{validation.animalIdentifierPattern}")
    //first two letters - animal animalType, second two letters - year of birth, third four letters - unique id
    //animalIdentifier is print on dog id tag
    private String animalIdentifier;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne
    private Person previousOwner;

    Animal() {
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Type getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Type animalType) {
        this.animalType = animalType;
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
        if (this.animalIdentifier == null) {
            this.animalIdentifier = animalIdentifier;
        }
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Person getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Person previousOwner) {
        this.previousOwner = previousOwner;
    }

    public enum Type {
        DOG("00"),
        CAT("01"),
        BIRD("02");

        private String typeIdentifier;

        Type(String typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
        }

        public String getTypeIdentifier() {
            return typeIdentifier;
        }
    }

    public enum AvailableForAdoption {
        AVAILABLE(true),
        UNDER_VETERINARY_CARE(false),
        ADOPTED(false),
        BEFORE_VACCINATION(false);

        private boolean isAvailable;

        AvailableForAdoption(boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public boolean isAvailable() {
            return isAvailable;
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalName='" + animalName + '\'' +
                ", animalType=" + animalType +
                ", behaviourDescription='" + behaviourDescription + '\'' +
                ", availableForAdoption=" + availableForAdoption +
                ", animalIdentifier='" + animalIdentifier + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", previousOwner=" + previousOwner +
                '}';
    }
}

