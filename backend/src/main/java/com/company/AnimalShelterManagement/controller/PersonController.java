package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
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
    public Iterable<PersonDTO> returnPeople() {
        return personService.returnPeople();
    }

    @GetMapping("${rest.person.getPerson}")
    public PersonDTO returnPerson(@PathVariable Long personId) {
        return mapToDTO(personService.returnPerson(personId));
    }

    @PostMapping(value = "${rest.person.postPerson}", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public PersonDTO savePerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.savePerson(personDTO);
    }

    @PutMapping(value = "${rest.person.putPerson}", consumes = APPLICATION_JSON_UTF8_VALUE)
    public PersonDTO updatePerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personDTO);
    }

    @DeleteMapping("${rest.person.deletePerson}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
        return ResponseEntity.status(OK).build();
    }

    private PersonDTO mapToDTO(Person person) {
        return personService.mapToDTO(person);
    }
}