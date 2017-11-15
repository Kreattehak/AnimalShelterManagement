package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.RANDOM_NUMBER;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @MockBean
    @Qualifier("defaultAnimalService")
    private AnimalService animalService;
    @Autowired
    private AnimalController animalController;

    @Test
    public void shouldPerformReturnAnimals() {
        when(animalService.returnAnimals()).thenReturn(new ArrayList<>());

        animalController.returnAnimals();

        verify(animalService).returnAnimals();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsAvailableForAdoptions() {
        when(animalService.returnAnimalsAvailableForAdoption()).thenReturn(new ArrayList<>());

        animalController.returnAnimalsAvailableForAdoption();

        verify(animalService).returnAnimalsAvailableForAdoption();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsCount() {
        when(animalService.countAnimals()).thenReturn(RANDOM_NUMBER);

        animalController.returnAnimalsCount();

        verify(animalService).countAnimals();
        verifyNoMoreInteractions(animalService);
    }
}