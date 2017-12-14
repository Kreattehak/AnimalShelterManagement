package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.utils.SearchForAnimalParams;
import org.springframework.data.domain.Page;

public interface AnimalService extends CommonService<Animal> {

    Iterable<Animal> returnAnimals();

    Page<Animal> returnAnimalsAvailableForAdoption(SearchForAnimalParams searchParams);

    Page<Animal> returnNotAdoptedAnimals(SearchForAnimalParams searchParams);

    Page<Animal> returnAnimalsWithLongestWaitingTime(Integer pageNumber, Integer pageSize);

    Page<Animal> returnRecentlyAddedAnimals(Integer pageNumber, Integer pageSize);

    Iterable<Animal> returnAnimalsOwnedByPerson(Long personId);

    Person returnPreviousOwner(Long animalId);

    long countAnimals();

    long[] countAnimalsForPeople();

    void addAnimalToPerson(Long personId, Long animalId);

    void deleteOwnedAnimal(Long personId, Long animalId);
}
