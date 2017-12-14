package com.company.AnimalShelterManagement.utils.resolvers;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class NotAdoptedRequestResolver extends RequestResolver {

    @Autowired
    public NotAdoptedRequestResolver(AnimalRepository animalRepository) {
        this.repository = animalRepository;
    }

    @Override
    public Page<Animal> findByName(Animal.Type type, String animalName, org.springframework.data.domain.Pageable pageable) {
        return repository.findNotAdoptedAnimalsByName(type, animalName, pageable);
    }

    @Override
    public Page<Animal> findByIdentifier(Animal.Type type, String animalIdentifier, Pageable pageable) {
        return repository.findNotAdoptedAnimalsByIdentifier(type, animalIdentifier, pageable);
    }

    @Override
    public Page<Animal> findWithoutParams(Pageable pageable) {
        return repository.findNotAdoptedAnimals(pageable);
    }
}