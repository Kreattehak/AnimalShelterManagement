package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.utils.ApiError;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException e) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ProcessUserRequestException.class)
    protected ResponseEntity<ApiError> handleInvalidProcessUserRequest(ProcessUserRequestException e) {
        ApiError apiError = new ApiError(UNPROCESSABLE_ENTITY);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
