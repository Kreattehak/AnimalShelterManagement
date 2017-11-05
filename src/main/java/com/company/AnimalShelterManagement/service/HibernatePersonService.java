package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HibernatePersonService implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public HibernatePersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> returnPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person returnPerson(Long personId) {
        return personRepository.findOne(personId);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Long personId) {
        personRepository.delete(personId);
    }
}