package com.company.AnimalShelterManagement.service.interfaces;

public interface CommonService<T> {

    T ifExistsReturnEntity(Long id);
}
