package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
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
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseProcessUserRequestException;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEqualityWithPerson;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
public class AddressControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressController addressController;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Address testAddress;
    private Person testPerson;
    private ResponseEntity<Address> response;

    private String home = "http://localhost:";
    private String apiForPerson = "/api/person/";
    private String addresses = "/addresses";
    private String address = "/address/";

    @Before
    public void setUp() {
        testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
        testAddress.setId(ID_VALUE);
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        testPerson.setId(ID_VALUE);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        home += port;
    }

    @Test
    public void shouldReturnPersonAddresses() {
        assertThatResponseHaveMultipleEntitiesReturned(home + apiForPerson + ID_VALUE + addresses,
                EXPECTED_ADDRESS_COUNT);
    }

    @Test
    public void shouldReturnAddress() {
        response = restTemplate.getForEntity(home + apiForPerson + ID_VALUE + address
                + ID_VALUE, Address.class);

        assertResponse(response, OK, is(checkAddressFieldsEquality(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME,
                ADDRESS_ZIP_CODE)));
    }

    @Test
    public void shouldSaveAddressAsAnotherAddressForPerson() {
        testAddress.setId(null);

        Address address = addressController.saveAddress(testAddress, ID_VALUE);

        assertThat(address, is(checkAddressFieldsEqualityWithPerson(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME,
                ADDRESS_ZIP_CODE, testPerson)));
        assertThat(address.getPerson(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
        assertThat(address.getPerson().getMainAddress(), not(equalTo(address)));
    }

    @Test
    public void shouldSaveFirstAddressAsMainAddressForPerson() {
        testAddress.setId(null);

        Address address = addressController.saveAddress(testAddress, ANOTHER_ID_VALUE);

        assertThat(address.getPerson().getMainAddress(), equalTo(address));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithSavedAddressData() {
        testAddress.setId(null);
        HttpEntity<Address> entity = new HttpEntity<>(testAddress, httpHeaders);

        response = restTemplate.postForEntity(home + apiForPerson + ID_VALUE
                + addresses, entity, Address.class);

        assertResponse(response, CREATED, is(checkAddressFieldsEquality(
                ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE)));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenAddressIdDoesNotExists() {
        checkResponseEntityNotFoundException(home + apiForPerson + ID_VALUE + address + ID_NOT_FOUND, GET);
    }

    @Test
    public void shouldUpdateAddress() {
        changeAddressData();

        addressController.updateAddress(testAddress);

        assertThat(addressRepository.findOne(ID_VALUE), is(checkAddressFieldsEqualityWithPerson(
                ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE, testPerson)));
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithUpdatedAddressData() {
        changeAddressData();
        HttpEntity<Address> entity = new HttpEntity<>(testAddress, httpHeaders);

        response = restTemplate.exchange(home + apiForPerson + ID_VALUE + address + ID_VALUE,
                PUT, entity, Address.class);

        assertResponse(response, OK, is(checkAddressFieldsEquality(ANOTHER_ADDRESS_STREET_NAME,
                ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE)));
    }

    @Test
    public void shouldDeleteAddress() {
        long countAfterDeletion = addressRepository.count() - 1;

        addressController.deleteAddress(ID_VALUE, ANOTHER_ID_VALUE);

        assertEquals(countAfterDeletion, addressRepository.count());
    }

    @Test
    @Sql(value = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Commit
    public void shouldResponseWithMessageAfterDeletingAddress() {
        ResponseEntity<String> response = restTemplate.exchange(home + apiForPerson + ID_VALUE + address
                + ANOTHER_ID_VALUE, DELETE, null, String.class);

        assertResponse(response, OK, containsString("Address with id: " + ANOTHER_ID_VALUE
                + " was successfully deleted"));
    }

    @Test
    public void shouldThrowProcessUserRequestExceptionWhenTryingToDeleteMainAddress() {
        long countBeforeDelete = addressRepository.count();

        String url = home + apiForPerson + ID_VALUE + address + ID_VALUE;
        String message = "Request could not be processed for ADDRESS, " + "for parameters: {address_id="
                + ID_VALUE + ", person_id=" + ID_VALUE + '}';

        checkResponseProcessUserRequestException(url, DELETE, message);

        assertEquals(addressRepository.count(), countBeforeDelete);
    }

    private void changeAddressData() {
        testAddress.setStreetName(ANOTHER_ADDRESS_STREET_NAME);
        testAddress.setCityName(ANOTHER_ADDRESS_CITY_NAME);
        testAddress.setZipCode(ANOTHER_ADDRESS_ZIP_CODE);
    }
}