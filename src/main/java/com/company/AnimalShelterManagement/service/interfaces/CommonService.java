package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

public interface CommonService {
     static <T> T ifExistsReturnEntity(Long id, CrudRepository<T, Long> repository, Class<T> clazz) {
         T t = repository.findOne(id);
         if (t == null) {
             throw new EntityNotFoundException(clazz, "id", id.toString());
         }

         return t;
     }
}