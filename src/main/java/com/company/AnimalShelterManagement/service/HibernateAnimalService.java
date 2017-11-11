package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO: Think about one abstract service
@Service("defaultAnimalService")
@Transactional
public class HibernateAnimalService implements AnimalService {

    private AnimalRepository animalRepository;

    public HibernateAnimalService() {
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Animal> returnAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Iterable<Animal> returnAnimalsAvailableForAdoption() {
        return animalRepository.findAnimalByAvailableForAdoption();
    }

    @Override
    public void generateIdentifier(Animal animal) {
        if (animal.getAnimalIdentifier() == null) {
            String firstPart = animal.getAnimalType().getTypeIdentifier();
            String secondPart = String.format("%02d", animal.getDateOfBirth().getYear() % 100);
            String thirdPart = String.format("%04d", animal.getId());
            animal.setAnimalIdentifier(firstPart + secondPart + thirdPart);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAnimals() {
        return animalRepository.count();
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

}