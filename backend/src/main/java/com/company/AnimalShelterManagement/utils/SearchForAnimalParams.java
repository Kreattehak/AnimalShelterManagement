package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public class SearchForAnimalParams implements Serializable {

    public static final int FIRST_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static final long serialVersionUID = 680458360909659050L;

    private Animal.Type animalType;
    private String animalIdentifier;
    private String animalName;
    private Integer number;
    private Integer size;

    public static Pageable createPagination(Integer number, Integer size) {
        number = (number != null ? number : FIRST_PAGE);
        size = (size != null ? size : DEFAULT_PAGE_SIZE);

        return new PageRequest(number, size);
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
