package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;

public interface PersonService extends CommonDTOService<Person, PersonDTO> {

    Iterable<PersonDTO> returnPeople();

    Person returnPerson(Long personId);

    PersonDTO savePerson(PersonDTO person);

    PersonDTO updatePerson(PersonDTO person);

    void deletePerson(Long personId);
}