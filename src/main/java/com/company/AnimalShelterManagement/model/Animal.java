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
import java.sql.Date;

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
    private String animalIdentifier;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @OneToOne
    @JoinTable(name = "animals_with_previous_owners", joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Person previousOwner;

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

//    public void setAnimalIdentifier(String animalIdentifier) {
//        this.animalIdentifier = animalIdentifier;
//    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Person getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Person previousOwner) {
        this.previousOwner = previousOwner;
    }

    private enum AnimalType {
        DOG, CAT, BIRD,
    }
}

