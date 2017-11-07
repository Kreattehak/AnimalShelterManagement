package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dog")
public class Dog extends Animal {

    @Column(name = "dog_race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race dogRace;

    public Dog() {
    }

    public Dog(String name, Type type, LocalDate dateOfBirth, Person previousOwner, Race dogRace) {
        super(name, type, dateOfBirth, previousOwner);
        this.dogRace = dogRace;
    }

    public Race getDogRace() {
        return dogRace;
    }

    public void setDogRace(Race dogRace) {
        this.dogRace = dogRace;
    }

    public enum Race {
        HUSKY, GERMAN_SHEPERD, CROSSBREAD,
    }

    @Override
    public String toString() {
        return super.toString() + ", dogRace=" + dogRace + '}';
    }
}

