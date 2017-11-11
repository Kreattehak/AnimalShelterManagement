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
import static com.company.AnimalShelterManagement.util.TestConstant.*;
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

    @Test
    public void shouldReturnAllPersonAddresses() {
        Person testPerson = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME);
        Address testAddress = new Address(ADDRESS_STREET_NAME, ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE);
        Address anotherTestAddress = new Address(ANOTHER_ADDRESS_STREET_NAME,
                ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE);

        this.entityManager.persist(testAddress);
        this.entityManager.persist(anotherTestAddress);
        testPerson.addAddress(testAddress);
        testPerson.addAddress(anotherTestAddress);
        Person personWithId = this.entityManager.persist(testPerson);

        Iterable<Address> addresses = addressRepository.findAddressesByPersonId(personWithId.getId());

        assertThat(addresses, allOf(
                hasItem(checkAddressFieldsEqualityWithPerson(ADDRESS_STREET_NAME,
                        ADDRESS_CITY_NAME, ADDRESS_ZIP_CODE, testPerson)),
                hasItem(checkAddressFieldsEqualityWithPerson(ANOTHER_ADDRESS_STREET_NAME,
                        ANOTHER_ADDRESS_CITY_NAME, ANOTHER_ADDRESS_ZIP_CODE, testPerson))));
    }
}