package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class DogController {

    private final DogService dogService;
    private final ModelMapper modelMapper;

    @Autowired
    public DogController(DogService dogService, ModelMapper modelMapper) {
        this.dogService = dogService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "${rest.dog.getDogs}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Iterable<DogDTO> returnDogs() {
        return dogService.returnDogs();
    }

    @GetMapping(value = "${rest.dog.getDog}", produces = APPLICATION_JSON_UTF8_VALUE)
    public DogDTO returnDog(@PathVariable Long dogId) {
        return mapToDTO(dogService.returnDog(dogId));
    }

    @PostMapping(value = "${rest.dog.postDog}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public DogDTO saveDog(@Valid @RequestBody DogDTO dog) {
        return dogService.saveDog(dog);
    }

    @PutMapping(value = "${rest.dog.putDog}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public DogDTO updateDog(@RequestBody DogDTO dogDTO) {
        System.out.println(dogDTO);
        return dogService.updateDog(dogDTO);
    }

    @DeleteMapping("${rest.dog.deleteDog}")
    public void deleteDog(@PathVariable Long dogId) {
        dogService.deleteDog(dogId);
    }

    private DogDTO mapToDTO(Dog dog) {
        return modelMapper.map(dog, DogDTO.class);
    }
}