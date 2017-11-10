package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.controller.PersonControllerIntegrationTest;
import com.company.AnimalShelterManagement.controller.PersonControllerTest;
import com.company.AnimalShelterManagement.repository.AddressRepositoryTest;
import com.company.AnimalShelterManagement.repository.AnimalRepositoryTest;
import com.company.AnimalShelterManagement.service.HibernateAddressServiceTest;
import com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest;
import com.company.AnimalShelterManagement.service.HibernatePersonServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddressRepositoryTest.class,
        AnimalRepositoryTest.class,
        HibernatePersonServiceTest.class,
        HibernateAddressServiceTest.class,
        HibernateAnimalServiceTest.class,
        PersonControllerTest.class,
        PersonControllerIntegrationTest.class
})
public class AnimalShelterManagementApplicationTests {
    //intentionally empty
}
