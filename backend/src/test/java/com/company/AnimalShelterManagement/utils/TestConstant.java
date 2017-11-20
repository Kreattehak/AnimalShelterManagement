package com.company.AnimalShelterManagement.utils;

import java.time.LocalDate;

public class TestConstant {
    public static final Long ID_VALUE = 10L;
    public static final Long ANOTHER_ID_VALUE = 11L;
    public static final Long ID_NOT_FOUND = 123123511L;
    public static final Long RANDOM_NUMBER = 12345L;
    public static final int NO_ENTITIES = 0;
    public static final int PEOPLE_COUNT = 7;
    public static final int DOGS_COUNT = 2;
    public static final int EXPECTED_ADDRESS_COUNT = 2;
    public static final int EXPECTED_ANIMALS_FOR_ADOPTION_COUNT = 3;
    public static final int EXPECTED_ANIMALS_COUNT = 4;

    public static final String FILL_UP = "00";
    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String PERSON_FIRST_NAME = "Test";
    public static final String ANOTHER_PERSON_FIRST_NAME = "Another";
    public static final String LAST_NAME = "lastName";
    public static final String ANOTHER_PERSON_LAST_NAME = "Data";
    public static final String PERSON_LAST_NAME = "Person";
    public static final String ADDRESS = "address";
    public static final String MAIN_ADDRESS = "mainAddress";

    public static final String CITY_NAME = "cityName";
    public static final String ADDRESS_CITY_NAME = "TestCity";
    public static final String ANOTHER_ADDRESS_CITY_NAME = "AnotherTestCity";
    public static final String STREET_NAME = "streetName";
    public static final String ADDRESS_STREET_NAME = "TestStreet";
    public static final String ANOTHER_ADDRESS_STREET_NAME = "AnotherTestStreet";
    public static final String ZIP_CODE = "zipCode";
    public static final String ADDRESS_ZIP_CODE = "11-111";
    public static final String ANOTHER_ADDRESS_ZIP_CODE = "99-999";
    public static final String PERSON = "person";

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String DATE_OF_BIRTH = "dateOfBirth";
    public static final String RACE = "dogRace";
    public static final String AVAILABLE_FOR_ADOPTION = "availableForAdoption";
    public static final String ANIMAL_BEHAVIOUR_DESCRIPTION = "Good boy";
    public static final String ANIMAL_IDENTIFIER_PATTERN = FILL_UP + String.format("%02d", LocalDate.now().getYear() % 100)
            + FILL_UP + ID_VALUE;

    public static final String DOG_NAME = "Sparky";
    public static final String ANOTHER_DOG_NAME = "AnotherDog";
    public static final String CAT_NAME = "Fluffy";
    public static final String BIRD_NAME = "Papi";

    public static final String STRING_TO_TEST_EQUALITY = ID_NOT_FOUND.toString();
}