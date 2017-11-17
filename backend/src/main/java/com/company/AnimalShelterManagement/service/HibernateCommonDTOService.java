package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.service.interfaces.CommonDTOService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

abstract class HibernateCommonDTOService<T, U, R extends CrudRepository<T, Long>> extends HibernateCommonService<T, R>
        implements CommonDTOService<T, U> {

    private final ModelMapper modelMapper;
    private final Class<U> mapTo;

    HibernateCommonDTOService(ModelMapper modelMapper, Class<T> mapFrom, Class<U> mapTo) {
        super(mapFrom);
        this.modelMapper = modelMapper;
        this.mapTo = mapTo;
    }

    @Override
    public Iterable<U> returnMappedEntities() {
        List<U> dtos = new ArrayList<>();
        repository.findAll().forEach(entity -> dtos.add(mapToDTO(entity)));

        return dtos;
    }

    @Override
    public U mapToDTO(T entity) {
        return modelMapper.map(entity, mapTo);
    }

    @Override
    public T mapFromDTO(U dto) {
        return modelMapper.map(dto, mapFrom);
    }
}