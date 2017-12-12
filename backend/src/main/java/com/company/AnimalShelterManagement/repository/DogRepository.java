package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DogRepository extends CrudRepository<Dog, Long> {

    @Query("SELECT d FROM Dog d WHERE d.availableForAdoption <> 'ADOPTED' ORDER BY d.dateOfRegistration ASC")
    Page<Dog> findNotAdoptedDogs(Pageable pageable);
}