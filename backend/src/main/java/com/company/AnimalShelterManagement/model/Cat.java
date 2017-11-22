package com.company.AnimalShelterManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cat")
public class Cat extends Animal {

    @Column(name = "cat_race", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race subType;

    public Cat() {
    }

    public Race getSubType() {
        return subType;
    }

    public void setSubType(Race subType) {
        this.subType = subType;
    }

    public enum Race {
        PERSIAN, ROOFLANDER
    }

    @Override
    public String toString() {
        return super.toString() + ", subType=" + subType + '}';
    }
}
