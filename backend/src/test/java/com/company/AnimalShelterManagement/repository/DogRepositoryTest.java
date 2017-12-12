package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Dog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.company.AnimalShelterManagement.model.Animal.Type.DOG;
import static com.company.AnimalShelterManagement.service.HibernateAnimalServiceTest.checkAnimalFieldsEquality;
import static com.company.AnimalShelterManagement.utils.AnimalFactory.newAvailableForAdoptionDog;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
public class DogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DogRepository dogRepository;

    private Dog testDog;

    @Before
    public void setUp() throws Exception {
        testDog = newAvailableForAdoptionDog(DOG_NAME, Dog.Race.GERMAN_SHEPERD);
        testDog.setDateOfBirth(DATE_OF_BIRTH_VALUE);
        entityManager.persist(testDog);
    }

    @Test
    public void shouldReturnAllDogsWithStatusOtherThanAdopted() {
        Pageable pageable = new PageRequest(FIRST_PAGE, EXPECTED_NOT_ADOPTED_DOGS_COUNT);

        Page<Dog> notAdoptedDogs = dogRepository.findNotAdoptedDogs(pageable);

        assertEquals(EXPECTED_NOT_ADOPTED_DOGS_COUNT, notAdoptedDogs.getNumberOfElements());
        assertThat(notAdoptedDogs, hasItem(checkAnimalFieldsEquality(
                DOG_NAME, DOG, DATE_OF_BIRTH_VALUE)));
    }
}