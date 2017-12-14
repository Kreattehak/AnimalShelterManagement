package com.company.AnimalShelterManagement.utils.resolvers;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public abstract class RequestResolver {

    AnimalRepository repository;

    public abstract Page<Animal> findByName(Animal.Type type, String animalName, Pageable pageable);

    public abstract Page<Animal> findByIdentifier(Animal.Type type, String animalIdentifier, Pageable pageable);

    public abstract Page<Animal> findWithoutParams(Pageable pageable);
}
