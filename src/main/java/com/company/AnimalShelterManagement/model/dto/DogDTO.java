package com.company.AnimalShelterManagement.model.dto;

import com.company.AnimalShelterManagement.model.Dog;

public class DogDTO extends AnimalDTO {

    private Dog.Race dogRace;

    public DogDTO() {
    }

    public Dog.Race getDogRace() {
        return dogRace;
    }

    public void setDogRace(Dog.Race dogRace) {
        this.dogRace = dogRace;
    }
}
