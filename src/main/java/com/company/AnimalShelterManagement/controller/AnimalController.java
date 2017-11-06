package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class AnimalController {

    private final AnimalService animalService;

    @Autowired

    public AnimalController(@Qualifier("defaultAnimalService") AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value = "${rest.animal.getAnimals}", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Animal> returnAnimals() {
        return animalService.returnAnimals();
    }
}