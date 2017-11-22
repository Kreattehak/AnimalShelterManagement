package com.company.AnimalShelterManagement.model.dto;

import com.company.AnimalShelterManagement.model.Dog;

public class DogDTO extends AnimalDTO {

    private Dog.Race subType;

    public Dog.Race getSubType() {
        return subType;
    }

    public void setSubType(Dog.Race subType) {
        this.subType = subType;
    }
}
