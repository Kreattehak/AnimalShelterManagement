package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.controller.*;
import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.repository.AddressRepositoryTest;
import com.company.AnimalShelterManagement.repository.AnimalRepositoryTest;
import com.company.AnimalShelterManagement.service.HibernateAddressServiceTest;
import com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest;
import com.company.AnimalShelterManagement.service.HibernateDogServiceTest;
import com.company.AnimalShelterManagement.service.HibernatePersonServiceTest;
import com.company.AnimalShelterManagement.utils.AnimalFactoryTest;
import com.company.AnimalShelterManagement.utils.AnimalShelterExceptionTest;
import com.company.AnimalShelterManagement.utils.RestResponsePage;
import org.hamcrest.Matcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Modifier.isPrivate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AnimalShelterExceptionTest.class,
        RestExceptionHandlerTest.class,
        AnimalFactoryTest.class,
        AddressRepositoryTest.class,
        AnimalRepositoryTest.class,
        HibernatePersonServiceTest.class,
        HibernateAddressServiceTest.class,
        HibernateAnimalServiceTest.class,
        HibernateDogServiceTest.class,
        PersonControllerTest.class,
        PersonControllerIntegrationTest.class,
        AddressControllerTest.class,
        AddressControllerIntegrationTest.class,
        AnimalControllerTest.class,
        AnimalControllerIntegrationTest.class,
        DogControllerTest.class,
        DogControllerIntegrationTest.class
})
public class AnimalShelterManagementApplicationTests {
    public static void assertThatResponseHaveMultipleEntitiesReturned(String url, int count) {
        ResponseEntity<List> response = new RestTemplate().getForEntity(url, List.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertEquals(count, response.getBody().size());
    }

    public static void assertThatResponseHaveMultipleEntitiesReturnedWithParams(String url, int count, Map<String, String> params) {
        ResponseEntity<List> response = new RestTemplate().getForEntity(url, List.class, params);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertEquals(count, response.getBody().size());
    }

    public static void assertThatResponseHavePagedEntitiesReturned(String url, int count) {
        ParameterizedTypeReference<RestResponsePage<Animal>> responseType =
                new ParameterizedTypeReference<RestResponsePage<Animal>>() {
                };

        ResponseEntity<RestResponsePage<Animal>> response = new RestTemplate().exchange(url, GET, null, responseType);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertEquals(count, response.getBody().getNumberOfElements());
    }

    public static <T> void testConstructorIsPrivate(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        assertTrue(isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    public static <T> void assertResponse(ResponseEntity<T> response, HttpStatus status, Matcher<T> matcher) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody(), matcher);
    }
}

