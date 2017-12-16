package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;

public interface DogService extends CommonDTOService<Dog, DogDTO> {

    Iterable<DogDTO> returnDogs();

    DogDTO returnDogDTO(Long dogId);

    Dog returnDog(Long dogId);

    Dog saveDog(Dog dog);

    Dog updateDog(Dog dog);

    void deleteDog(Long dogId);
}
