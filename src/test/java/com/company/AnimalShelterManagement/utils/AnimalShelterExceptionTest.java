package com.company.AnimalShelterManagement.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.InvocationTargetException;

import static com.company.AnimalShelterManagement.AnimalShelterManagementApplicationTests.testConstructorIsPrivate;

public class AnimalShelterExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenParametersAreNotEven() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid entries!");
        AnimalShelterException.toMap("1", "2", "3");
    }

    @Test
    public void testAnimalShelterExceptionConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        testConstructorIsPrivate(AnimalShelterException.class);
    }
}