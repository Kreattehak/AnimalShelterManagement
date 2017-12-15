package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.utils.SearchForAnimalParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Animal> returnAnimalsAvailableForAdoption(SearchForAnimalParams searchParams) {
        return animalService.returnAnimalsAvailableForAdoption(searchParams);
    }

    @GetMapping("${rest.animal.getNotAdoptedAnimals}")
    public Page<Animal> returnNotAdoptedAnimals(SearchForAnimalParams searchParams) {
        return animalService.returnNotAdoptedAnimals(searchParams);
    }

    @GetMapping("${rest.animal.getAnimalsWithLongestWaitingTime}")
    public Page<Animal> returnAnimalsWithLongestWaitingTime(Pageable pageable) {
        return animalService.returnAnimalsWithLongestWaitingTime(pageable);
    }

    @GetMapping("${rest.animal.getRecentlyAddedAnimals}")
    public Page<Animal> returnRecentlyAddedAnimals(Pageable pageable) {
        return animalService.returnRecentlyAddedAnimals(pageable);
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