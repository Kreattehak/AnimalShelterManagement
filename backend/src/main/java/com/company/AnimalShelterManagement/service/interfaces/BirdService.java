package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Bird;

public interface BirdService {

    Iterable<Bird> returnBirds();

    Bird returnBird(Long birdId);

    Bird saveBird(Bird bird);

    Bird updateBird(Bird bird);

    void deleteBird(Long birdId);
}
