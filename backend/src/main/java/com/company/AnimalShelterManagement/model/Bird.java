package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bird")
public class Bird extends Animal {

    //TODO: Update length to the longest enum
    @Column(name = "bird_species", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Species subType;

    public Bird() {
    }

    public Species getSubType() {
        return subType;
    }

    public void setSubType(Species subType) {
        this.subType = subType;
    }

    public enum Species {
        PARROT, CANARY, COCKATIEL, AFRICAN_GREY
    }

    @Override
    public String toString() {
        return super.toString() + ", subType=" + subType + '}';
    }
}

