package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import org.springframework.data.domain.Page;

public interface AnimalService extends CommonService<Animal> {

    Iterable<Animal> returnAnimals();

    Iterable<Animal> returnAnimalsAvailableForAdoption(Animal.Type animalType, String animalIdentifier, String animalName);

    Page<Animal> returnAnimalsWithLongestWaitingTime();

    Iterable<Animal> returnAnimalsOwnedByPerson(Long personId);

    Person returnPreviousOwner(Long animalId);

    long countAnimals();

    long[] countAnimalsForPeople();

    void addAnimalToPerson(Long personId, Long animalId);

    void deleteOwnedAnimal(Long personId, Long animalId);
}
