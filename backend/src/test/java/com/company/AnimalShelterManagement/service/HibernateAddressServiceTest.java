package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import com.company.AnimalShelterManagement.utils.ProcessUserRequestException;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateAddressServiceTest {

    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private PersonService personService;
    @Autowired
    private HibernateAddressService hibernateAddressService;

    private Person testPerson;
    private Address testAddress;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
    }

    @Test
    public void shouldReturnPersonAddresses() {
        when(addressRepository.findAddressesByPersonId(anyLong())).thenReturn(new ArrayList<>());

        hibernateAddressService.returnPersonAddresses(ID_VALUE);

        verify(addressRepository).findAddressesByPersonId(anyLong());
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldReturnAddress() {
        when(addressRepository.findOne(anyLong())).thenReturn(testAddress);

        hibernateAddressService.returnAddress(ID_VALUE);

        verify(addressRepository).findOne(anyLong());
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldThrowExceptionWhenAddressIdWasNotFound() {
        when(addressRepository.findOne(anyLong())).thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("was not found");
        hibernateAddressService.returnAddress(ID_VALUE);
    }

    @Test
    public void shouldSaveAddress() {
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);
        when(personService.returnPerson(anyLong())).thenReturn(testPerson);

        hibernateAddressService.saveAddress(testAddress, ID_VALUE);

        assertThat(testPerson.getAddress(), contains(equalTo(testAddress)));
        assertThat(testPerson.getMainAddress(), equalTo(testAddress));

        verify(addressRepository).save(any(Address.class));
        verify(personService).returnPerson(anyLong());
        verifyNoMoreInteractions(addressRepository);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldUpdateAddress() {
        Address anotherTestAddress = new Address(
                ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE);

        when(addressRepository.findOne(anyLong())).thenReturn(testAddress);
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);

        hibernateAddressService.updateAddress(anotherTestAddress);

        assertThat(testAddress, is(checkAddressFieldsEquality(ANOTHER_ADDRESS_STREET_NAME,
                ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE)));

        verify(addressRepository).findOne(anyLong());
        verify(addressRepository).save(any(Address.class));
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    public void shouldDeleteAddress() {
        Address anotherTestAddress = setUpPersonWithTwoAddresses();

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(addressRepository.findOne(anyLong())).thenReturn(anotherTestAddress);

        hibernateAddressService.deleteAddress(ID_VALUE, ID_VALUE);

        assertThat(testPerson.getAddress(), contains(equalTo(testAddress)));
        assertThat(testAddress.getPerson(), equalTo(testPerson));

        verify(addressRepository).findOne(anyLong());
        verify(addressRepository).delete(anyLong());
        verify(personService).returnPerson(anyLong());
        verifyNoMoreInteractions(addressRepository);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void shouldThrowExceptionWhenTryToDeleteMainAddress() {
        testPerson.addAddress(testAddress);

        when(personService.returnPerson(anyLong())).thenReturn(testPerson);
        when(addressRepository.findOne(anyLong())).thenReturn(testAddress);

        expectedException.expect(ProcessUserRequestException.class);
        expectedException.expectMessage("{address_id=" + ID_VALUE + ", person_id=" + ID_VALUE + '}');
        hibernateAddressService.deleteAddress(ID_VALUE, ID_VALUE);
    }

    public static Matcher<Address> checkAddressFieldsEquality(
            String streetName, String cityName, String zipCode) {
        return allOf(
                hasProperty(STREET_NAME, is(streetName)),
                hasProperty(CITY_NAME, is(cityName)),
                hasProperty(ZIP_CODE, is(zipCode)),
                hasProperty(PERSON, nullValue()));
    }

    public static Matcher<Address> checkAddressFieldsEqualityWithPerson(
            String streetName, String cityName, String zipCode, Person person) {
        return allOf(
                hasProperty(STREET_NAME, is(streetName)),
                hasProperty(CITY_NAME, is(cityName)),
                hasProperty(ZIP_CODE, is(zipCode)),
                hasProperty(PERSON, is(equalTo(person))));
    }

    private Address setUpPersonWithTwoAddresses() {
        Address anotherTestAddress = new Address(
                ANOTHER_ADDRESS_STREET_NAME, ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE);
        testAddress.setId(ID_VALUE);
        anotherTestAddress.setId(ANOTHER_ID_VALUE);
        testPerson.addAddress(testAddress);
        testPerson.addAddress(anotherTestAddress);

        return anotherTestAddress;
    }
}