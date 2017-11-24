package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
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

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertResponse;
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
@Transactional
public class PersonControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
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
        response = restTemplate.getForEntity(home + apiForPerson + ID_VALUE, PersonDTO.class);

        assertResponse(response, OK, equalTo(testPersonDTO));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenPersonIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForPerson + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldSavePerson() {
        personController.savePerson(testPersonDTO);

        assertThat(personRepository.findOne(ID_VALUE), checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithSavedPersonData() {
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);

        response = restTemplate.postForEntity(home + apiForPeople, entity, PersonDTO.class);

        assertResponse(response, CREATED, is(checkPersonDtoFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldUpdatePerson() {
        changePersonData();

        personController.updatePerson(testPersonDTO);

        assertThat(personRepository.findOne(ID_VALUE), is(checkPersonFieldsEquality(
                ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithUpdatedPersonData() {
        changePersonData();
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);

        response = restTemplate.exchange(home + apiForPerson + ID_VALUE, PUT, entity, PersonDTO.class);

        assertResponse(response, OK, is(checkPersonDtoFieldsEquality(ANOTHER_PERSON_FIRST_NAME,
                ANOTHER_PERSON_LAST_NAME)));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        long countAfterDeletion = personRepository.count() - 1;

        personController.deletePerson(ID_VALUE);

        assertEquals(countAfterDeletion, personRepository.count());
    }

    private void changePersonData() {
        testPersonDTO.setFirstName(ANOTHER_PERSON_FIRST_NAME);
        testPersonDTO.setLastName(ANOTHER_PERSON_LAST_NAME);
    }
}