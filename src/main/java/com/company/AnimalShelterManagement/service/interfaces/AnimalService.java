package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;

public interface AnimalService {

    Iterable<Animal> returnAnimals();

    Iterable<Animal> returnAnimalsAvailableForAdoption();

    Long countAnimals();
}
