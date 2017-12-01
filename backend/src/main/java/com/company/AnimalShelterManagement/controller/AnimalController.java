package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(@Qualifier("defaultAnimalService") AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value = "${rest.animal.getAnimals}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Animal> returnAnimals() {
        return animalService.returnAnimals();
    }

    @GetMapping(value = "${rest.animal.getAnimalsAvailableForAdoption}",
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Animal> returnAnimalsAvailableForAdoption() {
        return animalService.returnAnimalsAvailableForAdoption();
    }

    @GetMapping(value = "${rest.person.getAnimalsOwnedByPerson}",
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Animal> returnAnimalsOwnedByPerson(@PathVariable Long personId) {
        return animalService.returnAnimalsOwnedByPerson(personId);
    }

    @GetMapping(value = "${rest.animal.previousOwner}",
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Person returnPreviousOwner(@PathVariable Long animalId) {
        return animalService.returnPreviousOwner(animalId);
    }

    @GetMapping(value = "${rest.animal.animalsCount}")
    public long returnAnimalsCount() {
        return animalService.countAnimals();
    }

    @GetMapping(value = "${rest.animal.animalsCountForPeople}")
    public long[] returnAnimalsCountForPeople() {
        return animalService.countAnimalsForPeople();
    }

    @DeleteMapping("${rest.person.deleteOwnedAnimal}")
    public ResponseEntity<String> deleteOwnedAnimal(@PathVariable Long personId, @PathVariable Long animalId) {
        animalService.deleteOwnedAnimal(personId, animalId);
        return new ResponseEntity<>("Animal with id: " + animalId + " was successfully deleted from person"
                + " with id: " + personId, OK);
    }
}