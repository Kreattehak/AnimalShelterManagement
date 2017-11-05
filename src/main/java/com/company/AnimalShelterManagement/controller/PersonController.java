package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
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

    @PostMapping("${rest.person.postPerson}")
    public Person savePerson(@Valid @RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("${rest.person.putPerson}")
    public Person updatePerson(@Valid @RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("${rest.person.deletePerson}")
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }
}