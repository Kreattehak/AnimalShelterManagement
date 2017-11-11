package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.company.AnimalShelterManagement.service.interfaces.CommonService.ifExistsReturnEntity;

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
    public Iterable<PersonDTO> returnPeople() {
        List<PersonDTO> peopleDTO = new ArrayList<>();
        personRepository.findAll().forEach(person -> peopleDTO.add(mapToDTO(person)));

        return peopleDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Person returnPerson(Long personId) {
        return ifExistsReturnEntity(personId, personRepository, Person.class);
    }

    @Override
    public PersonDTO savePerson(PersonDTO personDTO) {
        Person person = mapFromDTO(personDTO);
        person = personRepository.save(person);

        return mapToDTO(person);
    }

    @Override
    public PersonDTO updatePerson(PersonDTO personDTO) {
        Person person = returnPerson(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person = personRepository.save(person);

        return mapToDTO(person);
    }

    @Override
    public void deletePerson(Long personId) {
        personRepository.delete(returnPerson(personId));
    }

    @Override
    public void addAddressForPerson(Address address, Long personId) {
        returnPerson(personId).addAddress(address);
    }

    private PersonDTO mapToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person mapFromDTO(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}