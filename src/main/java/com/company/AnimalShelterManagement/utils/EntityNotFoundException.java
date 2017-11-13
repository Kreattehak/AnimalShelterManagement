package com.company.AnimalShelterManagement.utils;

import java.util.Map;

/**
 * There is javax.persistence.EntityNotFoundException class but this implementation provides
 * more meaningful message which is generated from params that user has or has not entered
 */
public class EntityNotFoundException extends AnimalShelterException {

    public EntityNotFoundException(Class clazz, String... searchParams) {
        super(EntityNotFoundException.generateMessage(
                clazz.getSimpleName(), toMap(searchParams)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return entity.toUpperCase() + " was not found for parameters: " + searchParams;
    }
}