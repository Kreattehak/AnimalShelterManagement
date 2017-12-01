package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseProcessUserRequestException;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class AnimalControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private AnimalController animalController;
    @Autowired
    private PersonService personService;

    private RestTemplate restTemplate;

    private String home = "http://localhost:";
    private String animalsResource = "/animals/";
    private String apiForAnimals = "/api/animals";
    private String apiForAnimal = "/api/animal/";
    private String apiForPerson = "/api/person/";
    private String previousOwner = "/previousOwner";
    private String animalsForAdoption = "/availableForAdoption";

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        home += port;
    }

    @Test
    public void shouldReturnAnimals() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForAnimals, EXPECTED_ANIMALS_COUNT);
    }

    @Test
    public void shouldReturnAnimalsAvailableForAdoptions() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForAnimals + animalsForAdoption,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT);
    }

    @Test
    public void shouldReturnAnimalsOwnedByPerson() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForPerson + ID_VALUE + animalsResource,
                EXPECTED_ANIMALS_FOR_PERSON_COUNT);
    }

    @Test
    public void shouldReturnPreviousOwner() {
        ResponseEntity<Person> response = restTemplate.getForEntity(home + apiForAnimal
                + ID_VALUE + previousOwner, Person.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldReturnAnimalsCount() {
        Long count = animalController.returnAnimalsCount();

        assertEquals(EXPECTED_ANIMALS_COUNT, count.intValue());
    }

    @Test
    public void shouldReturnAnimalsCountForPeople() {
        long[] animalsCount = animalController.returnAnimalsCountForPeople();
        int animalsCountForOnlyPerson = (int) animalsCount[0];

        assertEquals(animalsCount.length, EXPECTED_ANIMALS_FOR_PERSON_COUNT);
        assertEquals(animalsCountForOnlyPerson, EXPECTED_ANIMALS_FOR_PERSON_COUNT);
    }

    @Test
    public void shouldDeleteOwnedAnimal() {
        animalController.deleteOwnedAnimal(ID_VALUE, ID_VALUE);

        assertThat(personService.returnPerson(ID_VALUE).getAnimal(), hasSize(NO_ENTITIES));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteNotOwnedAnimal() {
        String url = home + apiForPerson + ID_VALUE + animalsResource + ANOTHER_ID_VALUE;
        String message = "Request could not be processed for PERSON, " + "for parameters: {person_id="
                + ID_VALUE + ", animal_id=" + ANOTHER_ID_VALUE + '}';

        checkResponseProcessUserRequestException(url, DELETE, message);

        assertThat(personService.returnPerson(ID_VALUE).getAnimal(), hasSize(EXPECTED_ANIMALS_FOR_PERSON_COUNT));
    }
}
