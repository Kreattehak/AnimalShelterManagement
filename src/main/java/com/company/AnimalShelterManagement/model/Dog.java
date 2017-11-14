package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dog")
public class Dog extends Animal {

    @Column(name = "dog_race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race dogRace;

    public Dog() {
    }

    public Race getDogRace() {
        return dogRace;
    }

    public void setDogRace(Race dogRace) {
        this.dogRace = dogRace;
    }

    public enum Race {
        HUSKY, GERMAN_SHEPERD, CROSSBREAD, ENGLISH_COCKER_SPANIEL,
        }

    @Override
    public String toString() {
        return super.toString() + ", dogRace=" + dogRace + '}';
    }
}

