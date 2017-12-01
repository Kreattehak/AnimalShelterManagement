package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("defaultAnimalService")
@Transactional(readOnly = true)
public class HibernateAnimalService extends HibernateCommonService<Animal, AnimalRepository>
        implements AnimalService {

    private final PersonService personService;

    @Autowired
    public HibernateAnimalService(AnimalRepository animalRepository, PersonService personService) {
        super(Animal.class);
        this.repository = animalRepository;
        this.personService = personService;
    }

    @Override
    public Iterable<Animal> returnAnimals() {
        return repository.findAll();
    }

    @Override
    public Iterable<Animal> returnAnimalsAvailableForAdoption() {
        return repository.findAnimalByAvailableForAdoption();
    }

    @Override
    public Iterable<Animal> returnAnimalsOwnedByPerson(Long personId) {
        return repository.findAnimalsOwnedByPerson(personId);
    }

    @Override
    public Person returnPreviousOwner(Long personId) {
        return repository.findPersonByAnimalId(personId);
    }

    @Override
    public long countAnimals() {
        return repository.count();
    }

    @Override
    public long[] countAnimalsForPeople() {
        return repository.findAnimalsCountForPeople();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOwnedAnimal(Long personId, Long animalId) {
        Person personFromDatabase = personService.returnPerson(personId);
        Animal animalFromDatabase = ifExistsReturnEntity(animalId);

        if (!personFromDatabase.removeAnimal(animalFromDatabase)) {
            throw new ProcessUserRequestException(Person.class, "person_id", personId.toString(),
                    "animal_id", animalId.toString());
        }
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.repository = animalRepository;
    }
}