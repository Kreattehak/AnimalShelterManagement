package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Dog;
import org.springframework.data.repository.CrudRepository;

public interface DogRepository extends CrudRepository<Dog, Long> {

}