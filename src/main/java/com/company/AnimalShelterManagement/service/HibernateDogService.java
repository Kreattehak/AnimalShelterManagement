package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.company.AnimalShelterManagement.service.interfaces.CommonService.ifExistsReturnEntity;

@Service
@Transactional
public class HibernateDogService extends HibernateAnimalService implements DogService {

    private final DogRepository dogRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HibernateDogService(DogRepository dogRepository, ModelMapper modelMapper) {
        this.dogRepository = dogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<DogDTO> returnDogs() {
        List<DogDTO> dogsDTO = new ArrayList<>();
        dogRepository.findAll().forEach(dog -> dogsDTO.add(mapToDTO(dog)));

        return dogsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Dog returnDog(Long dogId) {
        return ifExistsReturnEntity(dogId, dogRepository, Dog.class);
    }

    @Override
    public DogDTO saveDog(DogDTO dogDTO) {
        Dog dog = mapFromDTO(dogDTO);
        dog = dogRepository.save(dog);
        super.generateIdentifier(dog);

        return mapToDTO(dog);
    }

    @Override
    public DogDTO updateDog(DogDTO dogDTO) {
        Dog dog = returnDog(dogDTO.getId());
        dog.setAnimalName(dogDTO.getAnimalName());
        dog.setDateOfBirth(dogDTO.getDateOfBirth());
        dog.setAvailableForAdoption(dogDTO.getAvailableForAdoption());

        return mapToDTO(dogRepository.save(dog));
    }

    @Override
    public void deleteDog(Long dogId) {
        dogRepository.delete(dogId);
    }

    private DogDTO mapToDTO(Dog dog) {
        return modelMapper.map(dog, DogDTO.class);
    }

    private Dog mapFromDTO(DogDTO dogDTO) {
        return modelMapper.map(dogDTO, Dog.class);
    }
}