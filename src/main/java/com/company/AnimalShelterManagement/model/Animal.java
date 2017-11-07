package com.company.AnimalShelterManagement.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

//TODO: Think about displaying animals available to adoption
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

    Animal(String animalName, Type animalType, LocalDate dateOfBirth, Person previousOwner) {
        this.animalName = animalName;
        this.animalType = animalType;
        this.dateOfBirth = dateOfBirth;
        this.previousOwner = previousOwner;
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

    @Override
    public String toString() {
        return "Animal{" +
                "animalName='" + animalName + '\'' +
                ", animalType=" + animalType +
                ", animalIdentifier='" + animalIdentifier + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", previousOwner=" + previousOwner;
    }
}

