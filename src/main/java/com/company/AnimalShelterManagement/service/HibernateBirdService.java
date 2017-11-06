package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Bird;
import com.company.AnimalShelterManagement.service.interfaces.BirdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateBirdService extends HibernateAnimalService implements BirdService {

    public HibernateBirdService() {
    }

    @Override
    public Iterable<Bird> returnBirds() {
        return birdRepository.findAll();
    }

    @Override
    public Bird returnBird(Long birdId) {
        return birdRepository.findOne(birdId);
    }

    @Override
    public Bird saveBird(Bird bird) {
        bird = birdRepository.save(bird);
        super.generateIdentifier(bird);
        return bird;
    }

    @Override
    public Bird updateBird(Bird bird) {
        return birdRepository.save(bird);
    }

    @Override
    public void deleteBird(Long birdId) {
        birdRepository.delete(birdId);
    }
}