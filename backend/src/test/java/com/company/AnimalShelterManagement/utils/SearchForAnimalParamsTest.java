package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;

public class SearchForAnimalParamsTest {

    public static SearchForAnimalParams returnNoParams() {
        return new SearchForAnimalParams();
    }

    public static SearchForAnimalParams returnAnimalTypeAndNameParams(Animal.Type animalType, String animalName) {
        SearchForAnimalParams searchParams = new SearchForAnimalParams();
        searchParams.setAnimalType(animalType);
        searchParams.setAnimalName(animalName);
        return searchParams;
    }

    public static SearchForAnimalParams returnAnimalTypeAndIdentifierParams(Animal.Type animalType,
                                                                            String animalIdentifier) {
        SearchForAnimalParams searchParams = new SearchForAnimalParams();
        searchParams.setAnimalType(animalType);
        searchParams.setAnimalIdentifier(animalIdentifier);
        return searchParams;
    }
}