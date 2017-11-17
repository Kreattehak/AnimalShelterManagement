package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class AnimalControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate;
    private Animal testAnimal;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AnimalController animalController;

    private String home = "http://localhost:";
    private String apiForAnimals = "/api/animals";
    private String apiForAnimal = "/api/animal/";
    private String previousOwner = "/previousOwner";
    private String animalsForAdoption = "/availableForAdoption";

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        home += port;
    }

    @Test
    public void shouldReturnAnimals() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForAnimals , EXPECTED_ANIMALS_COUNT);
    }

    @Test
    public void shouldReturnAnimalsAvailableForAdoptions() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForAnimals + animalsForAdoption,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT);
    }

    @Test
    public void shouldReturnPreviousOwner() {
        setUpAnimalWithPersonInDatabase();
        ResponseEntity<Person> response = restTemplate.getForEntity(home + apiForAnimal
                + testAnimal.getId() + previousOwner, Person.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
    }

    @Test
    public void shouldReturnAnimalsCount() {
        Long count = animalController.returnAnimalsCount();

        assertEquals(EXPECTED_ANIMALS_COUNT, count.intValue());
    }

    private void setUpAnimalWithPersonInDatabase() {
        Person person = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        person =  personRepository.save(person);
        testAnimal = AnimalFactory.newAvailableForAdoptionDog(DOG_NAME, GERMAN_SHEPERD);
        testAnimal.setPreviousOwner(person);
        animalRepository.save(testAnimal);
    }
}
