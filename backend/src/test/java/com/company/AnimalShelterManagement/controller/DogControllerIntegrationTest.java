package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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
import java.util.HashMap;
import java.util.Map;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.*;
import static com.company.AnimalShelterManagement.controller.AnimalControllerIntegrationTest.WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER;
import static com.company.AnimalShelterManagement.controller.AnimalControllerIntegrationTest.WITH_PAGE_SIZE_PARAMETER;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseEntityNotFoundException;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.*;
import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.service.HibernateDogServiceTest.checkDogDtoFieldsEquality;
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
    @Autowired
    private ModelMapper mapper;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Dog testDog;
    private ResponseEntity<DogDTO> response;

    private String home = "http://localhost:";
    private String apiForDogs = "/api/dogs";
    private String apiForDog = "/api/dog/";
    private String notAdoptedDogs = "/notAdopted";

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

    @Test
    public void shouldReturnAllDogsWithStatusOtherThanAdoptedWithPageSizeParameter() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForDogs + notAdoptedDogs
                + WITH_PAGE_SIZE_PARAMETER, EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnAllDogsWithStatusOtherThanAdoptedWithPageSizeAndNumberParameters() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));
        params.put(PAGE_NUMBER, Integer.toString(SECOND_PAGE));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForDogs + notAdoptedDogs
                + WITH_PAGE_SIZE_PARAMETER + WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER, NO_ENTITIES, params);
    }

    @Test
    public void shouldReturnAllDogsWithStatusOtherThanAdoptedWithoutParameters() {
        assertThatResponseHavePagedEntitiesReturned(home + apiForDogs + notAdoptedDogs,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT);
    }

    @Test
    public void shouldReturnDog() throws Exception {
        response = restTemplate.getForEntity(home + apiForDog + ID_VALUE, DogDTO.class);

        assertResponse(response, OK, is(checkDogDtoFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                DATE_OF_BIRTH_VALUE, ADOPTED)));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenDogIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForDog + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldSaveDog() {
        testDog.setId(null);
        DogDTO dogDTO = dogController.saveDog(mapper.map(testDog, DogDTO.class));

        assertThat(dogRepository.findOne(dogDTO.getId()), is(checkDogFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                LocalDate.now(), AVAILABLE)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithSavedDogData() {
        testDog.setId(null);
        HttpEntity<DogDTO> entity = new HttpEntity<>(mapper.map(testDog, DogDTO.class), httpHeaders);

        response = restTemplate.postForEntity(home + apiForDogs, entity, DogDTO.class);

        assertResponse(response, CREATED, is(checkDogDtoFieldsEquality(DOG_NAME, GERMAN_SHEPERD,
                LocalDate.now(), AVAILABLE)));
    }

    @Test
    public void shouldUpdateDog() {
        changeDogData();

        dogController.updateDog(mapper.map(testDog, DogDTO.class));

        assertThat(dogRepository.findOne(ID_VALUE), is(checkDogFieldsEquality(ANOTHER_DOG_NAME,
                GERMAN_SHEPERD, DATE_OF_BIRTH_VALUE, UNDER_VETERINARY_CARE)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithUpdatedDogData() {
        changeDogData();
        HttpEntity<DogDTO> entity = new HttpEntity<>(mapper.map(testDog, DogDTO.class), httpHeaders);

        response = restTemplate.exchange(home + apiForDog + ID_VALUE, PUT, entity, DogDTO.class);

        assertResponse(response, OK, is(checkDogDtoFieldsEquality(ANOTHER_DOG_NAME, GERMAN_SHEPERD,
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