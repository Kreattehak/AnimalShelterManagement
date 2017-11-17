package com.company.AnimalShelterManagement.service.interfaces;

public interface CommonDTOService<T, U> {

    Iterable<U> returnMappedEntities();

    U mapToDTO(T entity);

    T mapFromDTO(U dto);
}
