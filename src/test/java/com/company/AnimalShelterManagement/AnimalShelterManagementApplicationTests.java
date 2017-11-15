package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.controller.*;
import com.company.AnimalShelterManagement.repository.AddressRepositoryTest;
import com.company.AnimalShelterManagement.repository.AnimalRepositoryTest;
import com.company.AnimalShelterManagement.service.HibernateAddressServiceTest;
import com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest;
import com.company.AnimalShelterManagement.service.HibernatePersonServiceTest;
import com.company.AnimalShelterManagement.utils.AnimalFactoryTest;
import com.company.AnimalShelterManagement.utils.AnimalShelterExceptionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AnimalShelterExceptionTest.class,
        AnimalFactoryTest.class,
        AddressRepositoryTest.class,
        AnimalRepositoryTest.class,
        HibernatePersonServiceTest.class,
        HibernateAddressServiceTest.class,
        HibernateAnimalServiceTest.class,
        PersonControllerTest.class,
        PersonControllerIntegrationTest.class,
        AddressControllerTest.class,
        AddressControllerIntegrationTest.class,
        AnimalControllerTest.class
})
public class AnimalShelterManagementApplicationTests {
    //intentionally empty
}
