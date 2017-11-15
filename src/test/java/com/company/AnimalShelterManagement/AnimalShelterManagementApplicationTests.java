package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.controller.*;
import com.company.AnimalShelterManagement.repository.AddressRepositoryTest;
import com.company.AnimalShelterManagement.repository.AnimalRepositoryTest;
import com.company.AnimalShelterManagement.service.HibernateAddressServiceTest;
import com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest;
import com.company.AnimalShelterManagement.service.HibernatePersonServiceTest;
import com.company.AnimalShelterManagement.utils.AnimalFactoryTest;
import com.company.AnimalShelterManagement.utils.AnimalShelterExceptionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
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
        PersonControllerTest.class,
        PersonControllerIntegrationTest.class,
        AddressControllerTest.class,
        AddressControllerIntegrationTest.class,
        AnimalControllerTest.class,
        AnimalControllerIntegrationTest.class
})
public class AnimalShelterManagementApplicationTests {
    public static void assertThatResponseHaveMultipleEntitiesReturned(String url, int count) {
        ResponseEntity<List> response = new RestTemplate().getForEntity(url, List.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertEquals(count, response.getBody().size());
    }
}
