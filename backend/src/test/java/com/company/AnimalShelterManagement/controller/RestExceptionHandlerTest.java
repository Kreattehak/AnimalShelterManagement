package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.AnimalShelterManagementApplication;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.utils.TestConstant.ID;
import static com.company.AnimalShelterManagement.utils.TestConstant.STRING_TO_TEST_EQUALITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestExceptionHandlerTest {

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    private static ResponseEntity<Object> response;
    private static RestTemplate restTemplate;

    @Test
    public void shouldReturnProperlyFormattedApiErrorResponse() throws Exception {
        EntityNotFoundException exception = new EntityNotFoundException(
                AnimalShelterManagementApplication.class, ID, STRING_TO_TEST_EQUALITY);

        response = restExceptionHandler.handleEntityNotFound(exception);

        assertApiErrorResponseForEntityNotFound();
    }

    static void checkResponseEntityNotFoundException(String url, HttpMethod method) {
        callForResponse(url, method);
        assertApiErrorResponseForEntityNotFound();
    }

    static void checkResponseProcessUserRequestException(String url, HttpMethod method, String message) {
        callForResponse(url, method);
        assertApiErrorResponseForProcessUserRequestException(message);
    }

    private static void assertApiErrorResponseForEntityNotFound() {
        String message = "was not found for parameters: {" + ID + '=' + STRING_TO_TEST_EQUALITY + '}';
        assertResponse(NOT_FOUND, message);
    }

    private static void assertApiErrorResponseForProcessUserRequestException(String message) {
        assertResponse(UNPROCESSABLE_ENTITY, message);
    }

    private static void assertResponse(HttpStatus status, String message) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().toString(), containsString(message));
    }

    private static void callForResponse(String url, HttpMethod method) {
        skipHandleErrorWhenNot404Found();
        response = restTemplate.exchange(url, method, null, Object.class);
    }

    private static void skipHandleErrorWhenNot404Found() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
    }
}