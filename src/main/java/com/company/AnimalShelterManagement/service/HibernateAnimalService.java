package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.CatRepository;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("defaultAnimalService")
@Transactional
public class HibernateAnimalService implements AnimalService {

    protected final CatRepository catRepository;
    protected final DogRepository dogRepository;

    @Autowired
    public HibernateAnimalService(CatRepository catRepository, DogRepository dogRepository) {
        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
    }

    @Override
    public List<Animal> returnAnimals() {
        List<Animal> animals = new ArrayList<>();
        catRepository.findAll().forEach(animals::add);
        dogRepository.findAll().forEach(animals::add);
        return animals;
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
}