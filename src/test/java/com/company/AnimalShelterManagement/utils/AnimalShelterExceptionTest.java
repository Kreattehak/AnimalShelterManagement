package com.company.AnimalShelterManagement.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AnimalShelterExceptionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenParametersAreNotEven() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid entries!");
        AnimalShelterException.toMap("1", "2", "3");
    }
}