package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Iterable<Address> findByPersonId(Long personId);
}