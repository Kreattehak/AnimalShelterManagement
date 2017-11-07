package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "bird")
public class Bird extends Animal {

    //TODO: Update lentght to the longest enum
    @Column(name = "bird_species", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Species birdSpecies;

    public Bird() {
    }

    public Bird(String name, Type type, LocalDate dateOfBirth, Person previousOwner, Species birdSpecies) {
        super(name, type, dateOfBirth, previousOwner);
        this.birdSpecies = birdSpecies;
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

