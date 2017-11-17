package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;

public interface AnimalService extends CommonService<Animal> {

    Iterable<Animal> returnAnimals();

    Iterable<Animal> returnAnimalsAvailableForAdoption();

    Person returnPreviousOwner(Long animalId);

    long countAnimals();
}
