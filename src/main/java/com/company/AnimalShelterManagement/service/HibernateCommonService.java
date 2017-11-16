package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.service.interfaces.CommonService;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

abstract class HibernateCommonService<T, R extends CrudRepository<T, Long>> implements CommonService<T> {

    R repository;
    final Class<T> mapFrom;

    HibernateCommonService(Class<T> mapFrom) {
        this.mapFrom = mapFrom;
    }

    @Override
    public T ifExistsReturnEntity(Long id) {
        T t = repository.findOne(id);
        if (t == null) {
            throw new EntityNotFoundException(mapFrom, "id", id.toString());
        }

        return t;
    }
}