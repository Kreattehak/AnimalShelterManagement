package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernatePersonService implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public HibernatePersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Person> returnPeople() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
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