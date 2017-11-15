package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.assertApiErrorResponse;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEqualityWithPerson;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class AddressControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Address testAddress;
    private Person testPerson;
    private ResponseEntity<Address> response;

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
    }

    @Test
    public void shouldReturnPersonAddresses() {
        ResponseEntity<List<Address>> response =
                restTemplate.exchange("http://localhost:" + port + "/person/1/addresses", GET,
                        null, new ParameterizedTypeReference<List<Address>>() {
                        });

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), hasSize(greaterThan(0)));
    }

    @Test
    public void shouldReturnAddress() {
        setUpAddressInDatabase();

        response = restTemplate.getForEntity("http://localhost:" + port + "/person/" + testPerson.getId()
                        + "/address/" + testAddress.getId(), Address.class);

        assertResponse(equalTo(testAddress));
    }

    @Test
    public void shouldSaveAddress() {
        setUpPersonInDatabase();

        Address address = addressService.saveAddress(testAddress, testPerson.getId());

        assertThat(address, is(checkAddressFieldsEqualityWithPerson(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME,
                ADDRESS_ZIP_CODE, testPerson)));
        assertThat(address.getPerson(), is(checkPersonFieldsEquality(PERSON_FIRST_NAME, PERSON_LAST_NAME)));
        assertThat(address.getPerson().getMainAddress(), equalTo(address));
    }

    @Test
    public void shouldResponseWithSavedAddressData() {
        HttpEntity<Address> entity = new HttpEntity<>(testAddress, httpHeaders);
        setUpPersonInDatabase();

        response = restTemplate.postForEntity("http://localhost:" + port + "/person/" + testPerson.getId()
                + "/addresses", entity, Address.class);

        assertResponse(is(checkAddressFieldsEquality(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE)));
    }

    @Test
    public void shouldReturnApiErrorResponseWhenAddressIdDoesNotExists() {
        skipHandleErrorWhenNot404Found();

        ResponseEntity<Object> response = restTemplate.exchange(
                "http://localhost:" + port + "/person/1/address/111", GET, null, Object.class);

        assertApiErrorResponse(response);
    }

    @Test
    public void shouldUpdateAddress() {
        setUpAddressInDatabase();
        Address anotherTestAddress = new Address(ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME,
                ANOTHER_ADDRESS_ZIP_CODE);
        anotherTestAddress.setId(testAddress.getId());

        HttpEntity<Address> entity = new HttpEntity<>(anotherTestAddress, httpHeaders);

        restTemplate.put("http://localhost:" + port + "/person/" + testPerson.getId()
                + "/address/" + testAddress.getId(), entity, Address.class);

        assertThat(addressService.returnAddress(testAddress.getId()), is(checkAddressFieldsEqualityWithPerson(
                ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE, testPerson)));
    }

    @Test
    public void shouldResponseWithUpdatedAddressData() {
        setUpAddressInDatabase();
        changeAddressData();

        HttpEntity<Address> entity = new HttpEntity<>(testAddress, httpHeaders);

        response = restTemplate.exchange("http://localhost:" + port + "/person/" + testPerson.getId()
                + "/address/" + testAddress.getId(), PUT, entity, Address.class);

        assertResponse(is(checkAddressFieldsEquality(ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME,
                ANOTHER_ADDRESS_ZIP_CODE)));
    }

    @Test
    public void shouldDeleteAddress() {
        long addressId = setUpTwoAddressesInDatabase();
        long countAfterDeletion = addressRepository.count() - 1;

        restTemplate.delete("http://localhost:" + port + "/person/" + testPerson.getId() + "/address/" + addressId);

        assertEquals(addressRepository.count(), countAfterDeletion);
    }

    @Test
    public void shouldNotDeleteMainAddress() {
        skipHandleErrorWhenNot404Found();
        setUpAddressInDatabase();
        long countBeforeDelete = addressRepository.count();

        ResponseEntity<Object> response = restTemplate.exchange("http://localhost:" + port + "/person/"
                + testPerson.getId() + "/address/" + testAddress.getId(), DELETE, null, Object.class);

        assertThat(response.getStatusCode(), equalTo(UNPROCESSABLE_ENTITY));
        assertThat(response.getBody().toString(), containsString("Request could not be processed for ADDRESS, "
                + "for parameters: {address_id=" + testAddress.getId() + ", person_id=" + testPerson.getId() + '}'));
        assertEquals(addressRepository.count(), countBeforeDelete);
    }

    private void assertResponse(Matcher<Address> matcher) {
        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), matcher);
    }

    private void changeAddressData() {
        testAddress.setStreetName(ANOTHER_ADDRESS_STREET_NAME);
        testAddress.setCityName(ANOTHER_ADDRESS_CITY_NAME);
        testAddress.setZipCode(ANOTHER_ADDRESS_ZIP_CODE);
    }

    private void setUpPersonInDatabase() {
        personRepository.save(testPerson);
    }

    private void setUpAddressInDatabase() {
        setUpPersonInDatabase();
        addressService.saveAddress(testAddress, testPerson.getId());
    }

    private Long setUpTwoAddressesInDatabase() {
        setUpAddressInDatabase();
        Address anotherTestAddress = new Address(ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME,
                ANOTHER_ADDRESS_ZIP_CODE);
        addressService.saveAddress(anotherTestAddress, testPerson.getId());

        return anotherTestAddress.getId();
    }

    private void skipHandleErrorWhenNot404Found() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
    }
}