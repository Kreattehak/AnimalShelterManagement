package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernatePersonService implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public HibernatePersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Person> returnPeople() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDTO returnPerson(Long personId) {
        return mapToDTO(ifExistsReturnPerson(personId));
    }

    @Override
    public PersonDTO savePerson(PersonDTO personDTO) {
        Person person = mapFromDTO(personDTO);
        person = personRepository.save(person);

        return mapToDTO(person);
    }

    @Override
    public PersonDTO updatePerson(PersonDTO personDTO) {
        Person person = ifExistsReturnPerson(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person = personRepository.save(person);

        return mapToDTO(person);
    }

    //TODO: Should I really search and then delete person?
    @Override
    public void deletePerson(Long personId) {
        personRepository.delete(ifExistsReturnPerson(personId));
    }

    @Override
    public void addAddressForPerson(Address address, Long personId) {
        ifExistsReturnPerson(personId).addAddress(address);
    }

    private Person ifExistsReturnPerson(Long personId) {
        Person person = personRepository.findOne(personId);
        if (person == null) {
            throw new EntityNotFoundException(Person.class, "personId", personId.toString());
        }

        return person;
    }

    private PersonDTO mapToDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person mapFromDTO(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}