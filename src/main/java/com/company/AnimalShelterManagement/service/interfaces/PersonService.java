package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Person;

public interface PersonService {

    Iterable<Person> returnPeople();

    Person returnPerson(Long personId);

    Person savePerson(Person person);

    Person updatePerson(Person person);

    void deletePerson(Long personId);
}
