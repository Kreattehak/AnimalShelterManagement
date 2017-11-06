package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateDogService implements DogService {

    private final DogRepository dogRepository;

    @Autowired
    public HibernateDogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Dog> returnDogs() {
        return dogRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Dog returnDog(Long dogId) {
        return dogRepository.findOne(dogId);
    }

    @Override
    public Dog saveDog(Dog dog) {
        return dogRepository.save(dog);
    }

    @Override
    public Dog updateDog(Dog dog) {
        return dogRepository.save(dog);
    }

    @Override
    public void deleteDog(Long dogId) {
        dogRepository.delete(dogId);
    }
}