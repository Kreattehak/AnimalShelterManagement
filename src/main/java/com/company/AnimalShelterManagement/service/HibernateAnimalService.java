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

@Service
@Transactional
public class HibernateAnimalService implements AnimalService {

    private final CatRepository catRepository;
    private final DogRepository dogRepository;

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
}