package com.company.AnimalShelterManagement.controller;

import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RestExceptionHandlerTest {

    public static void assertApiErrorResponse(ResponseEntity<Object> response) {
        assertThat(response.getStatusCode(), equalTo(NOT_FOUND));
        assertThat(response.getBody().toString(), containsString("was not found for parameters: {id=111}"));
    }
}