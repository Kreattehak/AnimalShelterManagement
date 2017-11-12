package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

abstract class CommonService<T, R extends CrudRepository<T, Long>> {

    R repository;
    final Class<T> mapFrom;

    CommonService(Class<T> mapFrom) {
        this.mapFrom = mapFrom;
    }

    T ifExistsReturnEntity(Long id) {
        T t = repository.findOne(id);
        if (t == null) {
            throw new EntityNotFoundException(mapFrom, "id", id.toString());
        }

        return t;
    }
}