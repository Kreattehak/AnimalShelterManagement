package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Bird;
import org.springframework.data.repository.CrudRepository;

public interface BirdRepository extends CrudRepository<Bird, Long> {

}