package com.company.AnimalShelterManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "cats")
public class Cat extends Animal {

    @Column(name = "race", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private CatRace race;

    public Cat() {

    }

    public Cat(String name, AnimalType type, LocalDate dateOfBirth, Person previousOwner, CatRace race) {
        super(name, type, dateOfBirth, previousOwner);
        this.race = race;
    }

    public CatRace getRace() {
        return race;
    }

    public void setRace(CatRace race) {
        this.race = race;
    }

    public enum CatRace {
        PERSIAN, ROOFLANDER
    }
}
