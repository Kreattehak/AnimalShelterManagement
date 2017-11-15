package com.company.AnimalShelterManagement.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.utils.TestConstant.ANIMALS_COUNT;
import static com.company.AnimalShelterManagement.utils.TestConstant.ONE_ENTITY;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class AnimalControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    @Autowired
    private AnimalController animalController;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
    }

    @Test
    public void shouldReturnAnimals() {
        assertThatResponseHaveMultipleEntitiesReturned("http://localhost:" + port + "/animals", ANIMALS_COUNT);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptions() {
        assertThatResponseHaveMultipleEntitiesReturned("http://localhost:" + port
                        + "/animals/availableForAdoption", ONE_ENTITY);
    }

    @Test
    public void shouldPerformReturnAnimalsCount() {
        Long count = animalController.returnAnimalsCount();

        assertEquals(ANIMALS_COUNT, count.intValue());
    }
}
