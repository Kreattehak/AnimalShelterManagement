package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("defaultAnimalService")
@Transactional
public class HibernateAnimalService extends CommonService<Animal, AnimalRepository> implements AnimalService {

    @Autowired
    public HibernateAnimalService(AnimalRepository animalRepository) {
        super(Animal.class);
        this.repository = animalRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Animal> returnAnimals() {
        return repository.findAll();
    }

    @Override
    public Iterable<Animal> returnAnimalsAvailableForAdoption() {
        return repository.findAnimalByAvailableForAdoption();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAnimals() {
        return repository.count();
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.repository = animalRepository;
    }

}