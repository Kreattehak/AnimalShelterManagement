package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.AnimalShelterManagementApplication;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.*;

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

    @Test
    public void shouldReturnCustomResponseOnHttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException exception =
                new HttpRequestMethodNotSupportedException(METHOD_NOT_SUPPORTED);
        response = restExceptionHandler.handleHttpRequestMethodNotSupported(
                exception, null, null, null);

        assertApiErrorResponseForHttpRequestMethodNotSupportedException();
    }

    static void checkResponseEntityNotFoundException(String url, HttpMethod method) {
        callForResponse(url, method, null);
        assertApiErrorResponseForEntityNotFound();
    }

    static void checkResponseProcessUserRequestException(String url, HttpMethod method, String message) {
        checkResponseProcessUserRequestExceptionWithBody(url, method, message, null);
    }

    static void checkResponseProcessUserRequestExceptionWithBody(String url, HttpMethod method, String message,
                                                                 HttpEntity<Object> body) {
        callForResponse(url, method, body);
        assertApiErrorResponseForProcessUserRequestException(message);
    }

    private static void assertApiErrorResponseForEntityNotFound() {
        String message = "was not found for parameters: {" + ID + '=' + STRING_TO_TEST_EQUALITY + '}';
        assertResponse(NOT_FOUND, message);
    }

    private static void assertApiErrorResponseForProcessUserRequestException(String message) {
        assertResponse(UNPROCESSABLE_ENTITY, message);
    }

    private static void assertApiErrorResponseForHttpRequestMethodNotSupportedException() {
        String message = "Request method '" + METHOD_NOT_SUPPORTED + "' not supported";
        assertResponse(METHOD_NOT_ALLOWED, message);
    }

    private static void assertResponse(HttpStatus status, String message) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody().toString(), containsString(message));
    }

    private static void callForResponse(String url, HttpMethod method, HttpEntity<Object> entity) {
        skipHandleErrorWhenNot404Found();
        response = restTemplate.exchange(url, method, entity, Object.class);
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