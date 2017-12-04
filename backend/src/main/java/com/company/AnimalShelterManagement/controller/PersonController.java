package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_UTF8_VALUE)
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("${rest.person.getPeople}")
    public Iterable<Person> returnPeople() {
        return personService.returnPeople();
    }

    @GetMapping("${rest.person.getPerson}")
    public Person returnPerson(@PathVariable Long personId) {
        return personService.returnPerson(personId);
    }

    @PostMapping(value = "${rest.person.postPerson}", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public Person savePerson(@Valid @RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping(value = "${rest.person.putPerson}", consumes = APPLICATION_JSON_UTF8_VALUE)
    public Person updatePerson(@Valid @RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("${rest.person.deletePerson}")
    public ResponseEntity<String> deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
        return new ResponseEntity<>("Person with id: " + personId + " was successfully deleted", OK);
    }
}