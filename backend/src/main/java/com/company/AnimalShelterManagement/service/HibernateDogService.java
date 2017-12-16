package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateDogService extends HibernateCommonDTOService<Dog, DogDTO, DogRepository>
        implements DogService {

    @Autowired
    public HibernateDogService(DogRepository dogRepository, ModelMapper modelMapper) {
        super(modelMapper, Dog.class, DogDTO.class);
        this.repository = dogRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<DogDTO> returnDogs() {
        return returnMappedEntities();
    }

    @Override
    @Transactional(readOnly = true)
    public DogDTO returnDogDTO(Long dogId) {
        return mapToDTO(ifExistsReturnEntity(dogId));
    }

    @Override
    @Transactional(readOnly = true)
    public Dog returnDog(Long dogId) {
        return ifExistsReturnEntity(dogId);
    }

    @Override
    public Dog saveDog(Dog dog) {
        repository.save(dog);
        AnimalFactory.generateAnimalIdentifier(dog);

        return dog;
    }

    @Override
    public Dog updateDog(Dog dog) {
        Dog dogFromDatabase = returnDog(dog.getId());
        dogFromDatabase.setName(dog.getName());
        dogFromDatabase.setDateOfBirth(dog.getDateOfBirth());
        dogFromDatabase.setAvailableForAdoption(dog.getAvailableForAdoption());

        return repository.save(dogFromDatabase);
    }

    @Override
    public void deleteDog(Long dogId) {
        repository.delete(returnDog(dogId));
    }
}