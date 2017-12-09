package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.ADOPTED;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;

@Service("defaultAnimalService")
@Transactional(readOnly = true)
public class HibernateAnimalService extends HibernateCommonService<Animal, AnimalRepository>
        implements AnimalService {

    private final PersonService personService;
    private final int DEFAULT_PAGE_SIZE = 10;

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
    public Iterable<Animal> returnAnimalsAvailableForAdoption(Animal.Type animalType, String animalIdentifier,
                                                              String animalName) {
        if (animalType != null) {
            if (animalName != null) {
                return repository.findAnimalByAvailableForAdoptionByName(animalType, animalName);
            } else {
                return repository.findAnimalByAvailableForAdoptionByIdentifier(animalType, animalIdentifier);
            }
        } else {
            System.out.println("TYPE NULL");
            return repository.findAnimalByAvailableForAdoption();
        }
    }

    @Override
    public Page<Animal> returnAnimalsWithLongestWaitingTime(Integer pageSize) {
        pageSize = (pageSize != null ? pageSize : DEFAULT_PAGE_SIZE);
        return repository.findAnimalsWithLongestWaitingTime(new PageRequest(0, pageSize));
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
    public void addAnimalToPerson(Long personId, Long animalId) {
        Person personFromDatabase = personService.returnPerson(personId);
        Animal animalFromDatabase = ifExistsReturnEntity(animalId);

        if (!isAnimalAvailable(animalFromDatabase)) {
            throw new ProcessUserRequestException(Animal.class, "animal_id", animalId.toString(),
                    "available", "false");
        }
        if (!canAddAnimalToPerson(personFromDatabase, animalFromDatabase)) {
            throw new ProcessUserRequestException(Person.class, "person_id", personId.toString(),
                    "animal_id", animalId.toString());
        }

        animalFromDatabase.setAvailableForAdoption(ADOPTED);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOwnedAnimal(Long personId, Long animalId) {
        Person personFromDatabase = personService.returnPerson(personId);
        Animal animalFromDatabase = ifExistsReturnEntity(animalId);

        if (!canRemoveOwnedAnimal(personFromDatabase, animalFromDatabase)) {
            throw new ProcessUserRequestException(Person.class, "person_id", personId.toString(),
                    "animal_id", animalId.toString());
        }

        animalFromDatabase.setAvailableForAdoption(AVAILABLE);
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.repository = animalRepository;
    }

    private boolean canAddAnimalToPerson(Person personFromDatabase, Animal animalFromDatabase) {
        return personFromDatabase.addAnimal(animalFromDatabase);
    }

    private boolean isAnimalAvailable(Animal animalFromDatabase) {
        return animalFromDatabase.getAvailableForAdoption().isAvailable();
    }

    private boolean canRemoveOwnedAnimal(Person personFromDatabase, Animal animalFromDatabase) {
        return personFromDatabase.removeAnimal(animalFromDatabase);
    }
}