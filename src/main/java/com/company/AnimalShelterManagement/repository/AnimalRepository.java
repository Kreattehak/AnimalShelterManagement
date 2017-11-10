package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE'")
    Iterable<Animal> findAnimalByAvailableForAdoption();
}