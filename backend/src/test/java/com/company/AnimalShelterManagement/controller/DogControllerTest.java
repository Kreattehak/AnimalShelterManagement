package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.model.dto.DogDTO;
import com.company.AnimalShelterManagement.model.dto.PersonDTO;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.utils.AnimalFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.company.AnimalShelterManagement.model.Dog.Race.GERMAN_SHEPERD;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DogController.class)
public class DogControllerTest {

    @MockBean
    private DogService dogService;
    @Autowired
    private DogController dogController;
    @Autowired
    private ModelMapper mapper;

    private Dog testDog;

    @Before
    public void setUp() {
        testDog = AnimalFactory.newAvailableForAdoptionDog(DOG_NAME, GERMAN_SHEPERD);
        testDog.setId(ID_VALUE);
        testDog.setDateOfBirth(LocalDate.now());
    }

    @Test
    public void shouldPerformReturnDogs() {
        when(dogService.returnDogs()).thenReturn(new ArrayList<>());

        dogController.returnDogs();

        verify(dogService).returnDogs();
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void shouldPerformReturnDog() {
        when(dogService.returnDog(anyLong())).thenReturn(testDog);
        when(dogService.mapToDTO(any(Dog.class))).thenReturn(mapper.map(testDog, DogDTO.class));

        dogController.returnDog(ID_VALUE);

        verify(dogService).returnDog(anyLong());
        verify(dogService).mapToDTO(any(Dog.class));
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void shouldPerformSaveDog() {
        DogDTO testDogDTO = mapper.map(testDog, DogDTO.class);
        when(dogService.saveDog(any(DogDTO.class))).thenReturn(testDogDTO);

        dogController.saveDog(testDogDTO);

        verify(dogService).saveDog(any(DogDTO.class));
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void shouldPerformUpdateDog() {
        DogDTO testDogDTO = mapper.map(testDog, DogDTO.class);
        when(dogService.updateDog(any(DogDTO.class))).thenReturn(testDogDTO);

        dogController.updateDog(testDogDTO);

        verify(dogService).updateDog(any(DogDTO.class));
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void shouldPerformDeleteDog() {
        dogController.deleteDog(ID_VALUE);

        verify(dogService).deleteDog(ID_VALUE);
        verifyNoMoreInteractions(dogService);
    }
}