package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseEntityNotFoundException;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.UNDER_VETERINARY_CARE;
import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.service.HibernateDogServiceTest.checkDogDtoFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernateDogServiceTest.checkDogFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:data-test.sql")
public class DogControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogController dogController;
    @Autowired
    private ModelMapper mapper;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Dog testDog;
    private ResponseEntity<DogDTO> response;

    private String home = "http://localhost:";
    private String apiForDogs = "/api/dogs";
    private String apiForDog = "/api/dog/";

    @Before
    public void setUp() {
        testDog = AnimalFactory.newAvailableForAdoptionDog(DOG_NAME, GERMAN_SHEPERD);
        testDog.setDateOfBirth(LocalDate.now());
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        home += port;
    }

    @Test
    public void shouldReturnDogs() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForDogs, DOGS_COUNT);
    }

    @Test
    public void shouldReturnDog() {
        setUpDogInDatabase();

        response = restTemplate.getForEntity(home + apiForDog + testDog.getId(), DogDTO.class);

        assertResponse(OK, equalTo(mapper.map(testDog, DogDTO.class)));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenDogIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForDog + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldSaveDog() {
        DogDTO dogDTO = dogController.saveDog(mapper.map(testDog, DogDTO.class));

        assertThat(dogRepository.findOne(dogDTO.getId()), is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                LocalDate.now(), AVAILABLE)));
    }

    @Test
    public void shouldResponseWithSavedDogData() {
        HttpEntity<DogDTO> entity = new HttpEntity<>(mapper.map(testDog, DogDTO.class), httpHeaders);

        response = restTemplate.postForEntity(home + apiForDogs, entity, DogDTO.class);

        assertResponse(CREATED, is(checkDogDtoFieldsEquality(DOG_NAME, GERMAN_SHEPERD, LocalDate.now(), AVAILABLE)));
    }

    @Test
    public void shouldUpdateDog() {
        setUpDogInDatabase();
        changeDogData();

        dogController.updateDog(mapper.map(testDog, DogDTO.class));

        assertThat(dogRepository.findOne(testDog.getId()), is(checkDogFieldsEquality(ANOTHER_DOG_NAME,
                GERMAN_SHEPERD, LocalDate.of(1999, 11, 5), UNDER_VETERINARY_CARE)));
    }

    @Test
    public void shouldResponseWithUpdatedDogData() {
        setUpDogInDatabase();
        changeDogData();
        HttpEntity<DogDTO> entity = new HttpEntity<>(mapper.map(testDog, DogDTO.class), httpHeaders);

        response = restTemplate.exchange(home + apiForDog + testDog.getId(), PUT, entity, DogDTO.class);

        assertResponse(OK, is(checkDogDtoFieldsEquality(ANOTHER_DOG_NAME, GERMAN_SHEPERD,
                LocalDate.of(1999, 11, 5), UNDER_VETERINARY_CARE)));
    }

    @Test
    public void shouldDeletePerson() {
        setUpDogInDatabase();
        long countAfterDeletion = dogRepository.count() - 1;

        restTemplate.delete(home + apiForDog + testDog.getId());

        assertEquals(countAfterDeletion, dogRepository.count());
    }

    private void assertResponse(HttpStatus status, Matcher<DogDTO> matcher) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody(), matcher);
    }

    private void changeDogData() {
        testDog.setName(ANOTHER_DOG_NAME);
        testDog.setDateOfBirth(LocalDate.of(1999, 11, 5));
        testDog.setAvailableForAdoption(UNDER_VETERINARY_CARE);
    }

    private void setUpDogInDatabase() {
        dogRepository.save(testDog);
    }
}
