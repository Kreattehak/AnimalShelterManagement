package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dog")
public class Dog extends Animal {

    @Column(name = "dog_race", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race subType;

    public Dog() {
    }

    public Race getSubType() {
        return subType;
    }

    public void setSubType(Race subType) {
        this.subType = subType;
    }

    public enum Race {
        HUSKY, GERMAN_SHEPERD, CROSSBREAD, ENGLISH_COCKER_SPANIEL,
        }

    @Override
    public String toString() {
        return super.toString() + ", subType=" + subType + '}';
    }
}

