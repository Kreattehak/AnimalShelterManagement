package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public class SearchForAnimalParams implements Serializable{

    public static final int FIRST_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static final long serialVersionUID = 680458360909659050L;

    private Animal.Type animalType;
    private String animalIdentifier;
    private String animalName;
    private Integer pageNumber;
    private Integer pageSize;

    public static Pageable createPagination(Integer pageNumber, Integer pageSize) {
        pageNumber = (pageNumber != null ? pageNumber : FIRST_PAGE);
        pageSize = (pageSize != null ? pageSize : DEFAULT_PAGE_SIZE);

        return new PageRequest(pageNumber, pageSize);
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
