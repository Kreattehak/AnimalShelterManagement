package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Long> {

}