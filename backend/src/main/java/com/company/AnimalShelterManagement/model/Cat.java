package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cat")
public class Cat extends Animal {

    @Column(name = "cat_race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race catRace;

    public Cat() {
    }

    public Race getCatRace() {
        return catRace;
    }

    public void setCatRace(Race catRace) {
        this.catRace = catRace;
    }

    public enum Race {
        PERSIAN, ROOFLANDER
    }

    @Override
    public String toString() {
        return super.toString() + ", catRace=" + catRace + '}';
    }
}
