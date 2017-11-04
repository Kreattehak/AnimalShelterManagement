package com.company.AnimalShelterManagement.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Animal extends BaseEntity {

    @Column(name = "name", nullable = false, length = 25)
    @Length(min = 3, message = "{validation.minLength}")
    @NotNull
    private String name;

    @Column(name = "animal_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AnimalType type;

    @Column(name = "animal_identifier", nullable = false, length = 8)
    @Pattern(regexp = "\\d{8}", message = "{validation.animalIdentifierPattern}")
    @NotNull
    //first two letters - animal type, second two letters - year of birth, third four letters - unique id
    //animalIdentifier is print on dog id tag
    private String animalIdentifier;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinTable(name = "animals_with_previous_owners", joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Person previousOwner;

    public Animal() {
    }

    public Animal(String name, AnimalType type, LocalDate dateOfBirth, Person previousOwner) {
        this.name = name;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
        this.previousOwner = previousOwner;
    }

    //TODO:Extract it to common service for any type of animal
    private String generateIdentifier() {
        String firstPart = type.getTypeIdentifier();
        String secondPart = String.valueOf(this.dateOfBirth.getYear() % 100);
        String thirdPart = String.format("%04d", getId());
        return firstPart + secondPart + thirdPart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public String getAnimalIdentifier() {
        return animalIdentifier;
    }

    //TODO: Is AnimalIdentifier generated?
//    public void setAnimalIdentifier(String animalIdentifier) {
//        this.animalIdentifier = animalIdentifier;
//    }

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

    public enum AnimalType {
        DOG("00"),
        CAT("01"),
        BIRD("02");

        private String typeIdentifier;

        AnimalType(String typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
        }

        public String getTypeIdentifier() {
            return typeIdentifier;
        }
    }
}

