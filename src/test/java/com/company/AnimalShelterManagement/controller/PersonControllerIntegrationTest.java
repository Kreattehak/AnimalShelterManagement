package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.junit.After;
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

import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonDtoFieldsEquality;
import static com.company.AnimalShelterManagement.util.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
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

    private PersonDTO testPersonDTO;
    private ResponseEntity<PersonDTO> response;

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        testPersonDTO = new PersonDTO(PERSON_ID, PERSON_FIRST_NAME, PERSON_LAST_NAME);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);

    }

    @After
    public void tearDown() throws Exception {
        testPersonDTO = null;
        restTemplate = null;
        httpHeaders = null;
    }

    @Test
    public void shouldReturnPerson() {
        testPersonDTO = setUpPersonInDatabase();
        response = restTemplate.getForEntity("http://localhost:" + port + "/person/" + testPersonDTO.getId(),
                PersonDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(testPersonDTO));
    }

    @Test
    public void shouldSavePerson() {
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);
        response = restTemplate
                .postForEntity("http://localhost:" + port + "/people", entity, PersonDTO.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), is(checkPersonDtoFieldsEquality(
                testPersonDTO.getFirstName(), testPersonDTO.getLastName())));
    }

    @Test
    public void shouldUpdatePerson() {
        testPersonDTO = setUpPersonInDatabase();
        testPersonDTO.setFirstName(ANOTHER_PERSON_FIRST_NAME);
        testPersonDTO.setLastName(ANOTHER_PERSON_LAST_NAME);
        HttpEntity<PersonDTO> entity = new HttpEntity<>(testPersonDTO, httpHeaders);

        restTemplate.put("http://localhost:" + port + "/person/" + testPersonDTO.getId(),
                entity, PersonDTO.class);

        assertThat(personService.returnPerson(testPersonDTO.getId()), is(
                checkPersonDtoFieldsEquality(ANOTHER_PERSON_FIRST_NAME, ANOTHER_PERSON_LAST_NAME)));
    }

    @Test
    public void shouldDeletePerson() {
        testPersonDTO = setUpPersonInDatabase();
        long countAfterDeletion = personRepository.count() - 1;

        restTemplate.delete("http://localhost:" + port + "/person/" + testPersonDTO.getId());

        assertEquals(personRepository.count(), countAfterDeletion);
    }

    private PersonDTO setUpPersonInDatabase() {
        return personService.savePerson(testPersonDTO);
    }
}
