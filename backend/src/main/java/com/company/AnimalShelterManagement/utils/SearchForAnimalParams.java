package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;

import java.io.Serializable;

public class SearchForAnimalParams implements Serializable{

    private static final long serialVersionUID = 680458360909659050L;

    private Animal.Type animalType;
    private String animalIdentifier;
    private String animalName;
    private Integer pageNumber;
    private Integer pageSize;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Animal.Type getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Animal.Type animalType) {
        this.animalType = animalType;
    }

    public String getAnimalIdentifier() {
        return animalIdentifier;
    }

    public void setAnimalIdentifier(String animalIdentifier) {
        this.animalIdentifier = animalIdentifier;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
