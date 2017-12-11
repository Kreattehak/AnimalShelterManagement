package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.utils.RestResponsePage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.company.AnimalShelterManagement.utils.TestConstant.*;
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
    public void shouldPerformReturnAnimalsAvailableForAdoption() {
        when(animalService.returnAnimalsAvailableForAdoption(any(Animal.Type.class), anyString(), anyString()))
                .thenReturn(new ArrayList<>());

        animalController.returnAnimalsAvailableForAdoption(null, null, null);

        verify(animalService).returnAnimalsAvailableForAdoption(any(Animal.Type.class), anyString(), anyString());
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsWithLongestWaitingTime() {
        when(animalService.returnAnimalsWithLongestWaitingTime(anyInt(), anyInt())).thenReturn(new RestResponsePage<>());

        animalController.returnAnimalsWithLongestWaitingTime(FIRST_PAGE, PAGE_SIZE_VALUE);

        verify(animalService).returnAnimalsWithLongestWaitingTime(anyInt(), anyInt());
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsOwnedByPerson() {
        when(animalService.returnAnimalsOwnedByPerson(anyLong())).thenReturn(new ArrayList<>());

        animalController.returnAnimalsOwnedByPerson(ID_VALUE);

        verify(animalService).returnAnimalsOwnedByPerson(anyLong());
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnPreviousOwner() {
        when(animalService.returnPreviousOwner(anyLong())).thenReturn(new Person());

        animalController.returnPreviousOwner(ID_VALUE);

        verify(animalService).returnPreviousOwner(anyLong());
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsCount() {
        when(animalService.countAnimals()).thenReturn(RANDOM_NUMBER);

        animalController.returnAnimalsCount();

        verify(animalService).countAnimals();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformReturnAnimalsCountForPeople() {
        when(animalService.countAnimalsForPeople()).thenReturn(new long[]{ID_VALUE, ANOTHER_ID_VALUE});

        animalController.returnAnimalsCountForPeople();

        verify(animalService).countAnimalsForPeople();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformAddAnimalToPerson() {
        animalController.addAnimalToPerson(ID_VALUE, ID_VALUE);

        verify(animalService).addAnimalToPerson(anyLong(), anyLong());
        verifyNoMoreInteractions(animalService);
    }

    @Test
    public void shouldPerformDeleteOwnedAnimal() {
        animalController.deleteOwnedAnimal(ID_VALUE, ID_VALUE);

        verify(animalService).deleteOwnedAnimal(anyLong(), anyLong());
        verifyNoMoreInteractions(animalService);
    }
}