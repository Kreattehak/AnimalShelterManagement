package com.company.AnimalShelterManagement.utils;

//TODO: Rework, simple message string is not enough
public class ProcessUserRequestException extends RuntimeException {

    public ProcessUserRequestException(String message) {
        super(message);
    }

    public ProcessUserRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
