package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping("${rest.getPeople}")
    public Person returnPeople() {
        return new Person();
    }

    @GetMapping("${rest.getPerson}")
    public Person returnPerson(@PathVariable Long personId) {
        return new Person();
    }

    @PostMapping("${rest.postPerson")
    public Person savePerson() {
        return new Person();
    }

    @PutMapping("${rest.putPerson")
    public Person updatePerson(@PathVariable Long personId) {
        return new Person();
    }

    @DeleteMapping("${rest.deletePerson")
    public Person deletePerson(@PathVariable Long personId) {
        return new Person();
    }
}