package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertResponse;
import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseEntityNotFoundException;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.*;
import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.service.HibernateDogServiceTest.checkDogFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class DogControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogController dogController;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Dog testDog;
    private ResponseEntity<Dog> response;

    private String home = "http://localhost:";
    private String apiForDogs = "/api/dogs";
    private String apiForDog = "/api/dog/";
    private String apiForDogDTO = "/api/dogDTO/";

    @Before
    public void setUp() {
        testDog = AnimalFactory.newAvailableForAdoptionDog(DOG_NAME, GERMAN_SHEPERD);
        testDog.setDateOfBirth(LocalDate.now());
        testDog.setId(ID_VALUE);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        home += port;
    }

    @Test
    public void shouldReturnDogs() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForDogs, DOGS_COUNT);
    }

    /* RestTemplate cast Dog to DogDTO, only difference is, that DogDTO doesn't have availableForAdoption field.
       In the test below I cast from DogDTO to Dog, just to see if that field is null
     */
    @Test
    public void shouldReturnDogDTO() throws Exception {
        ResponseEntity<Dog> response = restTemplate.getForEntity(home + apiForDogDTO + ID_VALUE, Dog.class);

        assertResponse(response, OK, is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD, DATE_OF_BIRTH_VALUE, null)));
    }

    @Test
    public void shouldReturnDog() throws Exception {
        response = restTemplate.getForEntity(home + apiForDog + ID_VALUE, Dog.class);

        assertResponse(response, OK, is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                DATE_OF_BIRTH_VALUE, ADOPTED)));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenDogIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForDog + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldSaveDog() {
        testDog.setId(null);
        dogController.saveDog(testDog);

        assertThat(dogRepository.findOne(testDog.getId()), is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                LocalDate.now(), AVAILABLE)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithSavedDogData() {
        testDog.setId(null);
        HttpEntity<Dog> entity = new HttpEntity<>(testDog, httpHeaders);

        response = restTemplate.postForEntity(home + apiForDogs, entity, Dog.class);

        assertResponse(response, CREATED, is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                LocalDate.now(), AVAILABLE)));
    }

    @Test
    public void shouldUpdateDog() {
        changeDogData();

        dogController.updateDog(testDog);

        assertThat(dogRepository.findOne(ID_VALUE), is(checkDogFieldsEquality(ANOTHER_DOG_NAME,
                GERMAN_SHEPERD, DATE_OF_BIRTH_VALUE, UNDER_VETERINARY_CARE)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithUpdatedDogData() {
        changeDogData();
        HttpEntity<Dog> entity = new HttpEntity<>(testDog, httpHeaders);

        response = restTemplate.exchange(home + apiForDog + ID_VALUE, PUT, entity, Dog.class);

        assertResponse(response, OK, is(checkDogFieldsEquality(ANOTHER_DOG_NAME, GERMAN_SHEPERD,
                DATE_OF_BIRTH_VALUE, UNDER_VETERINARY_CARE)));
    }

    @Test
    public void shouldDeleteDog() {
        long countAfterDeletion = dogRepository.count() - 1;

        dogController.deleteDog(ID_VALUE);

        assertEquals(countAfterDeletion, dogRepository.count());
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithMessageAfterDeletingDog() {
        ResponseEntity<String> response = restTemplate.exchange(home + apiForDog + ID_VALUE, DELETE,
                null, String.class);

        assertResponse(response, OK, containsString("Dog with id: " + ID_VALUE + " was successfully deleted"));
    }

    private void changeDogData() {
        testDog.setName(ANOTHER_DOG_NAME);
        testDog.setDateOfBirth(DATE_OF_BIRTH_VALUE);
        testDog.setAvailableForAdoption(UNDER_VETERINARY_CARE);
    }
}