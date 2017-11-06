package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;

public interface DogService {

    Iterable<Dog> returnDogs();

    Dog returnDog(Long dogId);

    Dog saveDog(Dog dog);

    Dog updateDog(Dog dog);

    void deleteDog(Long dogId);
}
