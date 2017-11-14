package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateDogService extends CommonDTOService<Dog, DogDTO, DogRepository> implements DogService {

    @Autowired
    public HibernateDogService(DogRepository dogRepository, ModelMapper modelMapper,
                               @Qualifier("defaultAnimalService") HibernateAnimalService hibernateAnimalService) {
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
    public Dog returnDog(Long dogId) {
        return ifExistsReturnEntity(dogId);
    }

    @Override
    public DogDTO saveDog(DogDTO dogDTO) {
        Dog dog = mapFromDTO(dogDTO);
        dog = repository.save(dog);
        AnimalFactory.generateAnimalIdentifier(dog);

        return mapToDTO(dog);
    }

    @Override
    public DogDTO updateDog(DogDTO dogDTO) {
        Dog dog = returnDog(dogDTO.getId());
        dog.setAnimalName(dogDTO.getAnimalName());
        dog.setDateOfBirth(dogDTO.getDateOfBirth());
        dog.setAvailableForAdoption(dogDTO.getAvailableForAdoption());

        return mapToDTO(repository.save(dog));
    }

    @Override
    public void deleteDog(Long dogId) {
        repository.delete(dogId);
    }
}