package com.company.AnimalShelterManagement.repository;

import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository<T> extends CrudRepository<T, Long> {
}