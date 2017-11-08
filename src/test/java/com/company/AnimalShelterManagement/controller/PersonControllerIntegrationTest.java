package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.util.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class PersonControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private HttpEntity<PersonDTO> entity;

    private PersonDTO testPersonDTO;
    private ResponseEntity<PersonDTO> response;

    @Autowired
    private PersonService personService;

    @Before
    public void setUp() {
        testPersonDTO = new PersonDTO(PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        entity = new HttpEntity<>(testPersonDTO, httpHeaders);
    }

    @Test
    public void shouldReturnPerson() {
        testPersonDTO = setUpPersonInDatabase();
        response = restTemplate
                .getForEntity("http://localhost:" + port + "/person/1", PersonDTO.class);

        //then
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(testPersonDTO));
    }

    private PersonDTO setUpPersonInDatabase() {
        return personService.savePerson(testPersonDTO);
    }
}
