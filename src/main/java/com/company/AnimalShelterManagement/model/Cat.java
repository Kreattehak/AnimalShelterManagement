package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cats")
public class Cat extends Animal {

    @Column(name = "race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race race;

    public Cat() {
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    private enum Race {
        PERSIAN, ROOFLANDER
    }
}
