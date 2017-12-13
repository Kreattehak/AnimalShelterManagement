package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_UTF8_VALUE)
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(@Qualifier("defaultAnimalService") AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("${rest.animal.getAnimals}")
    public Iterable<Animal> returnAnimals() {
        return animalService.returnAnimals();
    }

    @GetMapping("${rest.animal.getAnimalsAvailableForAdoption}")
    public Iterable<Animal> returnAnimalsAvailableForAdoption(@RequestParam(required = false) Animal.Type animalType,
                                                              @RequestParam(required = false) String animalIdentifier,
                                                              @RequestParam(required = false) String animalName) {
        return animalService.returnAnimalsAvailableForAdoption(animalType, animalIdentifier, animalName);
    }

    @GetMapping("${rest.animal.getNotAdoptedAnimals}")
    public Page<Animal> returnNotAdoptedAnimals(@RequestParam(required = false) Animal.Type animalType,
                                                @RequestParam(required = false) String animalIdentifier,
                                                @RequestParam(required = false) String animalName,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer pageSize) {
        return animalService.returnNotAdoptedAnimals(animalType, animalIdentifier, animalName, pageNumber, pageSize);
    }

    @GetMapping("${rest.animal.getAnimalsWithLongestWaitingTime}")
    public Page<Animal> returnAnimalsWithLongestWaitingTime(@RequestParam(required = false) Integer pageNumber,
                                                            @RequestParam(required = false) Integer pageSize) {
        return animalService.returnAnimalsWithLongestWaitingTime(pageNumber, pageSize);
    }

    @GetMapping("${rest.animal.getRecentlyAddedAnimals}")
    public Page<Animal> returnRecentlyAddedAnimals(@RequestParam(required = false) Integer pageNumber,
                                                   @RequestParam(required = false) Integer pageSize) {
        return animalService.returnRecentlyAddedAnimals(pageNumber, pageSize);
    }

    @GetMapping("${rest.person.getAnimalsOwnedByPerson}")
    public Iterable<Animal> returnAnimalsOwnedByPerson(@PathVariable Long personId) {
        return animalService.returnAnimalsOwnedByPerson(personId);
    }

    @GetMapping("${rest.animal.previousOwner}")
    public Person returnPreviousOwner(@PathVariable Long animalId) {
        return animalService.returnPreviousOwner(animalId);
    }

    @GetMapping("${rest.animal.animalsCount}")
    public long returnAnimalsCount() {
        return animalService.countAnimals();
    }

    @GetMapping("${rest.animal.animalsCountForPeople}")
    public long[] returnAnimalsCountForPeople() {
        return animalService.countAnimalsForPeople();
    }

    @PutMapping("${rest.person.addAnimalToPerson}")
    public ResponseEntity<String> addAnimalToPerson(@PathVariable Long personId, @PathVariable Long animalId) {
        animalService.addAnimalToPerson(personId, animalId);
        return new ResponseEntity<>("Animal with id: " + animalId + " was successfully added to person"
                + " with id: " + personId, OK);
    }

    @DeleteMapping("${rest.person.deleteOwnedAnimal}")
    public ResponseEntity<String> deleteOwnedAnimal(@PathVariable Long personId, @PathVariable Long animalId) {
        animalService.deleteOwnedAnimal(personId, animalId);
        return new ResponseEntity<>("Animal with id: " + animalId + " was successfully deleted from person"
                + " with id: " + personId, OK);
    }
}