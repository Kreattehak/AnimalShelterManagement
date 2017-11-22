package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseEntityNotFoundException;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonDtoFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
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
public class PersonControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonController personController;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private PersonDTO testPersonDTO;
    private ResponseEntity<PersonDTO> response;

    private String home = "http://localhost:";
    private String apiForPeople = "/api/people";
    private String apiForPerson = "/api/person/";

    @Before
    public void setUp() {
        testPersonDTO = new PersonDTO(ID_VALUE, PERSON_FIRST_NAME, PERSON_LAST_NAME);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        home += port;
    }

    @Test
    public void shouldReturnPeople() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForPeople, PEOPLE_COUNT);
    }

    @Test
    public void shouldReturnPerson() {
        setUpPersonInDatabase();

        response = restTemplate.getForEntity(home + apiForPerson + testPersonDTO.getId(),
                PersonDTO.class);

        assertResponse(OK, equalTo(testPersonDTO));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenPersonIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForPerson + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldSavePerson() {
        PersonDTO personDTO = personController.savePerson(testPersonDTO);

        assertThat(personRepository.findOne(personDTO.getId()), checkPersonFieldsEquality(PERSON_FIRST_NAME,
                PERSON_LAST_NAME));
    }

    @Test
    public void shouldResponseWithSavedPersonData() {
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);

        response = restTemplate
                .postForEntity(home + apiForPeople, entity, PersonDTO.class);

        assertResponse(CREATED, is(checkPersonDtoFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldUpdatePerson() {
        setUpPersonInDatabase();
        changePersonData();

        personController.updatePerson(testPersonDTO);

        assertThat(personRepository.findOne(testPersonDTO.getId()), is(
                checkPersonFieldsEquality(ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME)));
    }

    @Test
    public void shouldResponseWithUpdatedPersonData() {
        setUpPersonInDatabase();
        changePersonData();
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);

        response = restTemplate.exchange(home + apiForPerson + testPersonDTO.getId(), PUT,
                entity, PersonDTO.class);

        assertResponse(OK, is(checkPersonDtoFieldsEquality(ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME)));
    }

    @Test
    public void shouldDeletePerson() {
        setUpPersonInDatabase();
        long countAfterDeletion = personRepository.count() - 1;

        restTemplate.delete(home + apiForPerson + testPersonDTO.getId());

        assertEquals(personRepository.count(), countAfterDeletion);
    }

    private void assertResponse(HttpStatus status, Matcher<PersonDTO> matcher) {
        assertThat(response.getStatusCode(), equalTo(status));
        assertThat(response.getBody(), matcher);
    }

    private void changePersonData() {
        testPersonDTO.setFirstName(ANOTHER_PERSON_FIRST_NAME);
        testPersonDTO.setLastName(ANOTHER_PERSON_LAST_NAME);
    }

    private void setUpPersonInDatabase() {
        testPersonDTO = personService.savePerson(testPersonDTO);
    }
}
