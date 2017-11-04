package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping("${rest.getAllPeople}")
    public Person returnPerson() {
        return new Person();
    }

    @GetMapping("${rest.getPerson}")
    public Person returnPerson(@RequestParam Long personId) {
        return new Person();
    }
}
