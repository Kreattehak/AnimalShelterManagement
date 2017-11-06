package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateDogService extends HibernateAnimalService implements DogService {

    public HibernateDogService() {

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
        dog = dogRepository.save(dog);
        super.generateIdentifier(dog);
        return dog;
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