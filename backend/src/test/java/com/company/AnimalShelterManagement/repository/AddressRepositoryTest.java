package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.company.AnimalShelterManagement.service.HibernateAddressServiceTest.checkAddressFieldsEqualityWithPerson;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;

    private Person testPerson;

    @Test
    public void shouldReturnAllPersonAddresses() {
        createAndPersistPersonWithTwoAddresses();

        Iterable<Address> addresses = addressRepository.findAddressesByPersonId(testPerson.getId());

        assertThat(addresses, allOf(
                hasItem(checkAddressFieldsEqualityWithPerson(ADDRESS_STREET_NAME,
                        ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE, testPerson)),
                hasItem(checkAddressFieldsEqualityWithPerson(ANOTHER_ADDRESS_STREET_NAME,
                        ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE, testPerson))));
    }

    private void createAndPersistPersonWithTwoAddresses() {
        testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Address testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
        Address anotherTestAddress = new Address(ANOTHER_ADDRESS_STREET_NAME,
                ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE);

        saveTwoAddressesInDatabase(testAddress, anotherTestAddress);

        assignTwoAddressesToPerson(testPerson, testAddress, anotherTestAddress);
    }

    private void assignTwoAddressesToPerson(Person testPerson, Address testAddress, Address anotherTestAddress) {
        testPerson.addAddress(testAddress);
        testPerson.addAddress(anotherTestAddress);
        entityManager.persist(testPerson);
    }

    private void saveTwoAddressesInDatabase(Address testAddress, Address anotherTestAddress) {
        entityManager.persist(testAddress);
        entityManager.persist(anotherTestAddress);
    }
}