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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.assertThatResponseHaveMultipleEntitiesReturned;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseEntityNotFoundException;
import static com.company.AnimalShelterManagement.controller.RestExceptionHandlerTest.checkResponseProcessUserRequestException;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEquality;
import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEqualityWithPerson;
import static com.company.AnimalShelterManagement.service.HibernatePersonServiceTest.checkPersonFieldsEquality;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.OK;
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
        assertThatResponseHaveMultipleEntitiesReturned("http://localhost:" + port + "/person/1/addresses",
                ADDRESSES_COUNT_FOR_FIRST_PERSON);
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
        checkResponseEntityNotFoundException("http://localhost:" + port + "/person/1/address/" + ID_NOT_FOUND, GET);
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
        setUpAddressInDatabase();
        long countBeforeDelete = addressRepository.count();
        String url = "http://localhost:" + port + "/person/" + testPerson.getId() + "/address/" + testAddress.getId();
        String message = "Request could not be processed for ADDRESS, " + "for parameters: {address_id="
                + testAddress.getId() + ", person_id=" + testPerson.getId() + '}';

        checkResponseProcessUserRequestException(url, DELETE, message);

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
}