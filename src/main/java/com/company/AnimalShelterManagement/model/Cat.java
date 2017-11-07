package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "cat")
public class Cat extends Animal {

    @Column(name = "cat_race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Race catRace;

    public Cat() {

    }

    public Cat(String name, Type type, LocalDate dateOfBirth, Person previousOwner, Race catRace) {
        super(name, type, dateOfBirth, previousOwner);
        this.catRace = catRace;
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
