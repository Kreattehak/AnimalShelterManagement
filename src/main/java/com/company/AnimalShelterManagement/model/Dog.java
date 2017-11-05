package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dogs")
public class Dog extends Animal {

    @Column(name = "race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DogRace race;

    public Dog() {
    }

    public Dog(String name, AnimalType type, LocalDate dateOfBirth, Person previousOwner, DogRace race) {
        super(name, type, dateOfBirth, previousOwner);
        this.race = race;
    }

    public DogRace getRace() {
        return race;
    }

    public void setRace(DogRace race) {
        this.race = race;
    }

    public enum DogRace {
        HUSKY, GERMAN_SHEPERD, CROSSBREAD,
    }
}

