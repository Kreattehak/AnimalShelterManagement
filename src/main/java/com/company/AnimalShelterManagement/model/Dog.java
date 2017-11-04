package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dogs")
public class Dog extends Animal {

    @Column(name = "race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race race;

    public Dog() {
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    private enum Race {
        HUSKY, GERMAN_SHEPERD, CROSSBREAD,
    }

}
