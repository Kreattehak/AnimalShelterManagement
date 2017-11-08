package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "${rest.person.getPeople}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Person> returnPeople() {
        return personService.returnPeople();
    }

    @GetMapping(value = "${rest.person.getPerson}", produces = APPLICATION_JSON_UTF8_VALUE)
    public PersonDTO returnPerson(@PathVariable Long personId) {
        return personService.returnPerson(personId);
    }

    @PostMapping(value = "${rest.person.postPerson}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public PersonDTO savePerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.savePerson(personDTO);
    }

    @PutMapping(value = "${rest.person.putPerson}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public PersonDTO updatePerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personDTO);
    }

    @DeleteMapping("${rest.person.deletePerson}")
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }
}