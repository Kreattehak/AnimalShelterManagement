package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE'")
    Iterable<Animal> findAnimalByAvailableForAdoption();

    @Query("SELECT a.previousOwner FROM Animal a WHERE a.id = ?1")
    Person findPersonByAnimalId(Long animalId);

    @Query("SELECT COUNT(p) FROM Person p JOIN p.animal GROUP BY p.id")
    long[] findAnimalsCountForPeople();
}