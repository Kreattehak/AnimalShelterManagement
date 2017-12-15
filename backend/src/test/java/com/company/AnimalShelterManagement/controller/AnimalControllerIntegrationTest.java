package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AnimalRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.*;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseProcessUserRequestException;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.ADOPTED;
import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
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
    private AnimalRepository animalRepository;
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
    private String notAdoptedAnimals = "/notAdopted";
    private String longestWaitingTime = "/longestWaitingTime";
    private String recentlyAdded = "/recentlyAdded";
    private String withAnimalType = "?animalType={animalType}";
    private String withAnimalNameAdditionalParameter = "&animalName={animalName}";
    private String withAnimalIdentifierAdditionalParameter = "&animalIdentifier={animalIdentifier}";
    public static final String WITH_PAGE_SIZE_PARAMETER = "?pageSize={pageSize}";
    public static final String WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER = "&pageNumber={pageNumber}";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void shouldReturnAnimalsAvailableForAdoptionWithNoDataProvided() {
        assertThatResponseHavePagedEntitiesReturned(home + apiForAnimals + animalsForAdoption,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT);
    }

    @Test
    public void shouldReturnAnimalsAvailableForAdoptionByName() {
        Map<String, String> params = new HashMap<>();
        params.put(ANIMAL_TYPE, DOG.toString());
        params.put(ANIMAL_NAME, AVAILABLE_ANIMAL_NAME);

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + animalsForAdoption
                + withAnimalType + withAnimalNameAdditionalParameter, EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnAnimalsAvailableForAdoptionByIdentifier() {
        Map<String, String> params = new HashMap<>();
        params.put(ANIMAL_TYPE, DOG.toString());
        params.put(ANIMAL_IDENTIFIER, AVAILABLE_ANIMAL_IDENTIFIER_VALUE);

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + animalsForAdoption
                + withAnimalType + withAnimalIdentifierAdditionalParameter, EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnPagedAnimalsAvailableForAdoption() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));
        params.put(PAGE_NUMBER, String.valueOf(FIRST_PAGE));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + animalsForAdoption
                        + WITH_PAGE_SIZE_PARAMETER + WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnNotAdoptedAnimalsWithNoDataProvided() {
        assertThatResponseHavePagedEntitiesReturned(home + apiForAnimals + notAdoptedAnimals,
                EXPECTED_ANIMALS_IN_SHELTER_COUNT);
    }

    @Test
    public void shouldReturnNotAdoptedAnimalsByName() {
        Map<String, String> params = new HashMap<>();
        params.put(ANIMAL_TYPE, DOG.toString());
        params.put(ANIMAL_NAME, AVAILABLE_ANIMAL_NAME);

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + notAdoptedAnimals
                + withAnimalType + withAnimalNameAdditionalParameter, ONE_ENTITY, params);
    }

    @Test
    public void shouldReturnNotAdoptedAnimalsByIdentifier() {
        Map<String, String> params = new HashMap<>();
        params.put(ANIMAL_TYPE, DOG.toString());
        params.put(ANIMAL_IDENTIFIER, AVAILABLE_ANIMAL_IDENTIFIER_VALUE);

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + notAdoptedAnimals
                + withAnimalType + withAnimalIdentifierAdditionalParameter, ONE_ENTITY, params);
    }

    @Test
    public void shouldReturnPagedNotAdoptedAnimals() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, String.valueOf(EXPECTED_ANIMALS_IN_SHELTER_COUNT));
        params.put(PAGE_NUMBER, String.valueOf(FIRST_PAGE));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + notAdoptedAnimals
                        + WITH_PAGE_SIZE_PARAMETER + WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER,
                EXPECTED_ANIMALS_IN_SHELTER_COUNT, params);
    }

    @Test
    public void shouldReturnAnimalsWithLongestWaitingTimeWithPageSizeParameter() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + longestWaitingTime
                + WITH_PAGE_SIZE_PARAMETER, EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnAnimalsWithLongestWaitingTimeWithPageSizeAndNumberParameters() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));
        params.put(PAGE_NUMBER, Integer.toString(SECOND_PAGE));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + longestWaitingTime
                        + WITH_PAGE_SIZE_PARAMETER + WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER,
                NO_ENTITIES, params);
    }

    @Test
    public void shouldReturnRecentlyAddedAnimalsWithoutParameters() {
        assertThatResponseHavePagedEntitiesReturned(home + apiForAnimals + recentlyAdded,
                EXPECTED_ANIMALS_IN_SHELTER_COUNT);
    }

    @Test
    public void shouldReturnRecentlyAddedAnimalsWithPageSizeParameter() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + recentlyAdded
                + WITH_PAGE_SIZE_PARAMETER, EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnRecentlyAddedAnimalsWithPageSizeAndNumberParameters() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, Integer.toString(EXPECTED_ANIMALS_FOR_ADOPTION_COUNT));
        params.put(PAGE_NUMBER, Integer.toString(SECOND_PAGE));

        assertThatResponseHavePagedEntitiesReturnedWithParams(home + apiForAnimals + recentlyAdded
                        + WITH_PAGE_SIZE_PARAMETER + WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER,
                EXPECTED_ANIMALS_FOR_ADOPTION_COUNT, params);
    }

    @Test
    public void shouldReturnAnimalsWithLongestWaitingTimeWithoutParameters() {
        assertThatResponseHavePagedEntitiesReturned(home + apiForAnimals + longestWaitingTime,
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
    public void shouldAddAnimalToPerson() {
        animalController.addAnimalToPerson(ID_VALUE, AVAILABLE_ANIMAL_ID);

        Set<Animal> adoptedAnimals = personService.returnPerson(ID_VALUE).getAnimal();
        assertThat(adoptedAnimals, hasSize(EXPECT_PERSON_WITH_TWO_ANIMALS));
        assertThat(adoptedAnimals, hasItem(hasProperty(ID, is(ID_VALUE))));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithMessageAfterAddingAnimalToPersonDog() {
        ResponseEntity<String> response = restTemplate.exchange(home + apiForPerson + ID_VALUE + animalsResource
                + AVAILABLE_ANIMAL_ID, PUT, null, String.class);

        assertResponse(response, OK, containsString("Animal with id: " + AVAILABLE_ANIMAL_ID
                + " was successfully added to person with id: " + ID_VALUE));
    }

    @Test
    public void shouldThrowExceptionWhenAnimalIsNotAvailable() {
        String url = home + apiForPerson + ID_VALUE + animalsResource + ANOTHER_ID_VALUE;
        String message = "Request could not be processed for ANIMAL, " + "for parameters: {animal_id="
                + ANOTHER_ID_VALUE + ", available=false}";

        checkResponseProcessUserRequestException(url, PUT, message);

        assertThat(personService.returnPerson(ID_VALUE).getAnimal(), hasSize(EXPECTED_ANIMALS_FOR_PERSON_COUNT));
    }

    @Test
    public void shouldThrowExceptionWhenAddTheSameAnimalToPerson() {
        setAdoptedAnimalToAvailable();

        expectedException.expect(ProcessUserRequestException.class);
        expectedException.expectMessage("Request could not be processed for PERSON, " + "for parameters: {person_id="
                + ID_VALUE + ", animal_id=" + ID_VALUE + '}');
        animalController.addAnimalToPerson(ID_VALUE, ID_VALUE);
    }

    @Test
    public void shouldSetAvailableToAdoptedAfterAdoption() {
        animalController.addAnimalToPerson(ID_VALUE, AVAILABLE_ANIMAL_ID);

        assertEquals(ADOPTED, animalRepository.findOne(AVAILABLE_ANIMAL_ID).getAvailableForAdoption());
    }

    @Test
    public void shouldDeleteOwnedAnimal() {
        animalController.deleteOwnedAnimal(ID_VALUE, ID_VALUE);

        assertThat(personService.returnPerson(ID_VALUE).getAnimal(), hasSize(NO_ENTITIES));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithMessageAfterDeletingOwnedAnimal() {
        ResponseEntity<String> response = restTemplate.exchange(home + apiForPerson + ID_VALUE + animalsResource
                + ID_VALUE, DELETE, null, String.class);

        assertResponse(response, OK, containsString("Animal with id: " + ID_VALUE
                + " was successfully deleted from person with id: " + ID_VALUE));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteNotOwnedAnimal() {
        String url = home + apiForPerson + ID_VALUE + animalsResource + ANOTHER_ID_VALUE;
        String message = "Request could not be processed for PERSON, " + "for parameters: {person_id="
                + ID_VALUE + ", animal_id=" + ANOTHER_ID_VALUE + '}';

        checkResponseProcessUserRequestException(url, DELETE, message);

        assertThat(personService.returnPerson(ID_VALUE).getAnimal(), hasSize(EXPECTED_ANIMALS_FOR_PERSON_COUNT));
    }

    @Test
    public void shouldSetAvailableToAvailableAfterDogWasAbandoned() {
        animalController.deleteOwnedAnimal(ID_VALUE, ID_VALUE);

        assertEquals(AVAILABLE, animalRepository.findOne(ID_VALUE).getAvailableForAdoption());
    }

    private void setAdoptedAnimalToAvailable() {
        Person person = personService.returnPerson(ID_VALUE);
        Animal adoptedAnimal = person.getAnimal().iterator().next();
        adoptedAnimal.setAvailableForAdoption(AVAILABLE);
        personService.updatePerson(person);
    }
}
