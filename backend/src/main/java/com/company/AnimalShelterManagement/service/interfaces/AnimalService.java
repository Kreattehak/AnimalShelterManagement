package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.utils.SearchForAnimalParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService extends CommonService<Animal> {

    Iterable<Animal> returnAnimals();

    Page<Animal> returnAnimalsAvailableForAdoption(SearchForAnimalParams searchParams);

    Page<Animal> returnNotAdoptedAnimals(SearchForAnimalParams searchParams);

    Page<Animal> returnAnimalsWithLongestWaitingTime(Pageable pageable);

    Page<Animal> returnRecentlyAddedAnimals(Pageable pageable);

    Iterable<Animal> returnAnimalsOwnedByPerson(Long personId);

    Person returnPreviousOwner(Long animalId);

    long countAnimals();

    long[] countAnimalsForPeople();

    void addAnimalToPerson(Long personId, Long animalId);

    void deleteOwnedAnimal(Long personId, Long animalId);
}
