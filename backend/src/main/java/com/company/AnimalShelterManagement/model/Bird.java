package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bird")
public class Bird extends Animal {

    //TODO: Update length to the longest enum
    @Column(name = "bird_species", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Species birdSpecies;

    public Bird() {
    }

    public Species getBirdSpecies() {
        return birdSpecies;
    }

    public void setBirdSpecies(Species birdSpecies) {
        this.birdSpecies = birdSpecies;
    }

    public enum Species {
        PARROT, CANARY, COCKATIEL, AFRICAN_GREY
    }

    @Override
    public String toString() {
        return super.toString() + ", birdSpecies=" + birdSpecies + '}';
    }
}

