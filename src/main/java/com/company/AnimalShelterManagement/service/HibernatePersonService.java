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

@Service
@Transactional
public class HibernatePersonService extends CommonDTOService<Person, PersonDTO, PersonRepository> implements PersonService {

    @Autowired
    public HibernatePersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        super(modelMapper, Person.class, PersonDTO.class);
        this.repository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PersonDTO> returnPeople() {
        return returnMappedEntities();
    }

    @Override
    @Transactional(readOnly = true)
    public Person returnPerson(Long personId) {
        return ifExistsReturnEntity(personId);
    }

    @Override
    public PersonDTO savePerson(PersonDTO personDTO) {
        Person person = mapFromDTO(personDTO);
        person = repository.save(person);

        return mapToDTO(person);
    }

    @Override
    public PersonDTO updatePerson(PersonDTO personDTO) {
        Person person = returnPerson(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person = repository.save(person);

        return mapToDTO(person);
    }

    @Override
    public void deletePerson(Long personId) {
        repository.delete(returnPerson(personId));
    }

    @Override
    public void addAddressForPerson(Address address, Long personId) {
        returnPerson(personId).addAddress(address);
    }
}