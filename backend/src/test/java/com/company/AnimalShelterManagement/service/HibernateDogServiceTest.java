package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import com.company.AnimalShelterManagement.utils.EntityNotFoundException;
import com.company.AnimalShelterManagement.utils.RestResponsePage;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.company.AnimalShelterManagement.model.Animal.AvailableForAdoption.AVAILABLE;
import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateDogServiceTest {

    @MockBean
    private DogRepository dogRepository;
    @Autowired
    private DogService dogService;
    @Autowired
    private ModelMapper mapper;

    private Dog testDog;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testDog = AnimalFactory.newAvailableForAdoptionDog(DOG_NAME, GERMAN_SHEPERD);
        testDog.setId(ID_VALUE);
        testDog.setDateOfBirth(LocalDate.now());
    }

    @Test
    public void shouldPerformReturnDogs() {
        when(dogRepository.findAll()).thenReturn(new ArrayList<>());

        dogService.returnDogs();

        verify(dogRepository).findAll();
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    public void shouldPerformReturnAllDogsWithStatusOtherThanAdopted() {
        when(dogRepository.findNotAdoptedDogs(any(Pageable.class))).thenReturn(new RestResponsePage<>());

        dogService.returnNotAdoptedDogs(FIRST_PAGE, PAGE_SIZE_VALUE);

        verify(dogRepository).findNotAdoptedDogs(any(Pageable.class));
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    public void shouldPerformReturnDog() {
        when(dogRepository.findOne(anyLong())).thenReturn(testDog);

        dogService.returnDog(ID_VALUE);

        verify(dogRepository).findOne(anyLong());
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    public void shouldThrowExceptionWhenDogIdWasNotFound() {
        when(dogRepository.findOne(anyLong())).thenReturn(null);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("was not found");
        dogService.returnDog(ID_VALUE);
    }

    @Test
    public void shouldPerformSaveDog() {
        when(dogRepository.save(any(Dog.class))).thenReturn(testDog);

        dogService.saveDog(mapper.map(testDog, DogDTO.class));

        verify(dogRepository).save(any(Dog.class));
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    public void shouldPerformUpdateDog() {
        when(dogRepository.findOne(anyLong())).thenReturn(testDog);
        when(dogRepository.save(any(Dog.class))).thenReturn(testDog);

        dogService.updateDog(mapper.map(setUpAnotherDog(), DogDTO.class));

        assertThat(testDog, is(checkDogFieldsEquality(ANOTHER_DOG_NAME, GERMAN_SHEPERD,
                DATE_OF_BIRTH_VALUE , AVAILABLE)));

        verify(dogRepository).findOne(anyLong());
        verify(dogRepository).save(any(Dog.class));
        verifyNoMoreInteractions(dogRepository);
    }

    @Test
    public void shouldPerformDeleteDog() {
        when(dogRepository.findOne(anyLong())).thenReturn(testDog);

        dogService.deleteDog(ID_VALUE);

        verify(dogRepository).findOne(anyLong());
        verify(dogRepository).delete(any(Dog.class));
        verifyNoMoreInteractions(dogRepository);
    }

    private Dog setUpAnotherDog() {
        Dog dog = AnimalFactory.newlyReceivedDog(ANOTHER_DOG_NAME, GERMAN_SHEPERD);
        dog.setId(ANOTHER_ID_VALUE);
        dog.setDateOfBirth(DATE_OF_BIRTH_VALUE );
        dog.setAvailableForAdoption(AVAILABLE);
        return dog;
    }

    public static Matcher<DogDTO> checkDogDtoFieldsEquality(String name, Dog.Race race, LocalDate dateOfBirth,
                                                            Animal.AvailableForAdoption availableForAdoption) {
        return allOf(
                hasProperty(TYPE, is(Animal.Type.DOG)),
                hasProperty(NAME, is(name)),
                hasProperty(SUB_TYPE, is(race)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)),
                hasProperty(AVAILABLE_FOR_ADOPTION, is(availableForAdoption)));
    }

    public static Matcher<Dog> checkDogFieldsEquality(String name, Dog.Race race, LocalDate dateOfBirth,
                                                      Animal.AvailableForAdoption availableForAdoption) {
        return allOf(
                hasProperty(TYPE, is(Animal.Type.DOG)),
                hasProperty(NAME, is(name)),
                hasProperty(SUB_TYPE, is(race)),
                hasProperty(DATE_OF_BIRTH, is(dateOfBirth)),
                hasProperty(AVAILABLE_FOR_ADOPTION, is(availableForAdoption)));
    }
}