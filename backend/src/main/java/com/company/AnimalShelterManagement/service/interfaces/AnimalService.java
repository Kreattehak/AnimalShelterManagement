package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;

public interface AnimalService extends CommonService<Animal> {

    Iterable<Animal> returnAnimals();

    Iterable<Animal> returnAnimalsAvailableForAdoption();

    Iterable<Animal> returnAnimalsOwnedByPerson(Long personId);

    Person returnPreviousOwner(Long animalId);

    long countAnimals();

    long[] countAnimalsForPeople();

    void deleteOwnedAnimal(Long personId, Long animalId);
}
