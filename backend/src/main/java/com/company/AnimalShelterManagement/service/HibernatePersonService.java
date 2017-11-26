package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernatePersonService extends HibernateCommonService<Person, PersonRepository>
        implements PersonService {

    @Autowired
    public HibernatePersonService(PersonRepository personRepository) {
        super(Person.class);
        this.repository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Person> returnPeople() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person returnPerson(Long personId) {
        return ifExistsReturnEntity(personId);
    }

    @Override
    public Person savePerson(Person person) {
        return repository.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        Person personFromDatabase = returnPerson(person.getId());
        personFromDatabase.setFirstName(person.getFirstName());
        personFromDatabase.setLastName(person.getLastName());

        return repository.save(personFromDatabase);
    }

    @Override
    public void deletePerson(Long personId) {
        repository.delete(returnPerson(personId));
    }
}