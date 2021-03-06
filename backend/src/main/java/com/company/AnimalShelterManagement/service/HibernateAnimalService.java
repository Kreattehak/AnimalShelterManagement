package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import com.company.AnimalShelterManagement.utils.SearchForAnimalParams;
import com.company.AnimalShelterManagement.utils.resolvers.AvailableForAdoptionRequestResolver;
import com.company.AnimalShelterManagement.utils.resolvers.NotAdoptedRequestResolver;
import com.company.AnimalShelterManagement.utils.resolvers.RequestResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.ADOPTED;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.utils.SearchForAnimalParams.createPagination;

@Service("defaultAnimalService")
@Transactional(readOnly = true)
public class HibernateAnimalService extends HibernateCommonService<Animal, AnimalRepository>
        implements AnimalService {

    private final PersonService personService;
    private final NotAdoptedRequestResolver notAdoptedRequestResolver;
    private final AvailableForAdoptionRequestResolver availableForAdoptionRequestResolver;

    @Autowired
    public HibernateAnimalService(AnimalRepository animalRepository, PersonService personService,
                                  NotAdoptedRequestResolver notAdoptedRequestResolver,
                                  AvailableForAdoptionRequestResolver availableForAdoptionRequestResolver) {
        super(Animal.class);
        this.repository = animalRepository;
        this.personService = personService;
        this.notAdoptedRequestResolver = notAdoptedRequestResolver;
        this.availableForAdoptionRequestResolver = availableForAdoptionRequestResolver;
    }

    @Override
    public Iterable<Animal> returnAnimals() {
        return repository.findAll();
    }

    @Override
    public Page<Animal> returnAnimalsAvailableForAdoption(SearchForAnimalParams searchParams) {
        Pageable pageable = createPagination(searchParams.getNumber(), searchParams.getSize());
        return returnAnimalsBySpecificRequestParameters(searchParams, pageable, availableForAdoptionRequestResolver);
    }

    @Override
    public Page<Animal> returnNotAdoptedAnimals(SearchForAnimalParams searchParams) {
        Pageable pageable = createPagination(searchParams.getNumber(), searchParams.getSize());
        return returnAnimalsBySpecificRequestParameters(searchParams, pageable, notAdoptedRequestResolver);
    }

    @Override
    public Page<Animal> returnAnimalsWithLongestWaitingTime(Pageable pageable) {
        return repository.findAnimalsWithLongestWaitingTime(pageable);
    }

    @Override
    public Page<Animal> returnRecentlyAddedAnimals(Pageable pageable) {
        return repository.findRecentlyAddedAnimals(pageable);
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

    private Page<Animal> returnAnimalsBySpecificRequestParameters(SearchForAnimalParams searchParams, Pageable pageable, RequestResolver resolver) {
        if (searchParams.getAnimalType() != null) {
            if (searchParams.getAnimalName() != null) {
                return resolver.findByName(searchParams.getAnimalType(), searchParams.getAnimalName(), pageable);
            } else {
                return resolver.findByIdentifier(searchParams.getAnimalType(), searchParams.getAnimalIdentifier(), pageable);
            }
        } else {
            return resolver.findWithoutParams(pageable);
        }
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