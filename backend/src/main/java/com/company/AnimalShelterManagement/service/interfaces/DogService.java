package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;

public interface DogService extends CommonDTOService<Dog, DogDTO> {

    Iterable<DogDTO> returnDogs();

    Iterable<Dog> returnNotAdoptedDogs(Integer pageNumber, Integer pageSize);

    Dog returnDog(Long dogId);

    DogDTO saveDog(DogDTO dogDTO);

    DogDTO updateDog(DogDTO dogDTO);

    void deleteDog(Long dogId);
}
