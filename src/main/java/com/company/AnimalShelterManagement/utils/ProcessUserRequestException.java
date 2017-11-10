package com.company.AnimalShelterManagement.utils;

import java.util.Map;

public class ProcessUserRequestException extends AnimalShelterException {

    public ProcessUserRequestException(Class clazz, String... searchParamsMap) {
        super(ProcessUserRequestException.generateMessage(
                clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return "Request could not be processed for " + entity.toUpperCase() + ", for parameters: " + searchParams;
    }
}
