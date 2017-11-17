package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("defaultAnimalService")
@Transactional(readOnly = true)
public class HibernateAnimalService extends HibernateCommonService<Animal, AnimalRepository>
        implements AnimalService {

    @Autowired
    public HibernateAnimalService(AnimalRepository animalRepository) {
        super(Animal.class);
        this.repository = animalRepository;
    }

    @Override
    public Iterable<Animal> returnAnimals() {
        return repository.findAll();
    }

    @Override
    public Iterable<Animal> returnAnimalsAvailableForAdoption() {
        return repository.findAnimalByAvailableForAdoption();
    }

    @Override
    public Person returnPreviousOwner(Long personId) {
        return repository.findPersonByAnimalId(personId);
    }

    @Override
    public long countAnimals() {
        return repository.count();
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.repository = animalRepository;
    }
}