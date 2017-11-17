package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}