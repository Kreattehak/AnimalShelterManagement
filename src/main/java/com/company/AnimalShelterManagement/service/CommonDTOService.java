package com.company.AnimalShelterManagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

abstract class CommonDTOService<T, U, R extends CrudRepository<T, Long>> extends CommonService<T, R> {

    private final ModelMapper modelMapper;
    private final Class<U> mapTo;

    CommonDTOService(ModelMapper modelMapper, Class<T> mapFrom, Class<U> mapTo) {
        super(mapFrom);
        this.modelMapper = modelMapper;
        this.mapTo = mapTo;
    }

    Iterable<U> returnMappedEntities() {
        List<U> dtos = new ArrayList<>();
        repository.findAll().forEach(entity -> dtos.add(mapToDTO(entity)));

        return dtos;
    }

    U mapToDTO(T entity) {
        return modelMapper.map(entity, mapTo);
    }

    T mapFromDTO(U dto) {
        return modelMapper.map(dto, mapFrom);
    }


}