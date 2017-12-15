package com.company.AnimalShelterManagement.utils;

import com.company.AnimalShelterManagement.model.Animal;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.company.AnimalShelterManagement.utils.SearchForAnimalParams.DEFAULT_PAGE_SIZE;
import static com.company.AnimalShelterManagement.utils.TestConstant.*;
import static org.junit.Assert.assertEquals;

public class SearchForAnimalParamsTest {

    public static final String WITH_PAGE_SIZE_PARAMETER = "?size={size}";
    public static final String WITH_PAGE_NUMBER_ADDITIONAL_PARAMETER = "&number={number}";

    public static SearchForAnimalParams returnNoParams() {
        return new SearchForAnimalParams();
    }

    public static SearchForAnimalParams returnAnimalTypeAndNameParams(Animal.Type animalType, String animalName) {
        SearchForAnimalParams searchParams = new SearchForAnimalParams();
        searchParams.setAnimalType(animalType);
        searchParams.setAnimalName(animalName);

        return searchParams;
    }

    public static SearchForAnimalParams returnAnimalTypeAndIdentifierParams(Animal.Type animalType,
                                                                            String animalIdentifier) {
        SearchForAnimalParams searchParams = new SearchForAnimalParams();
        searchParams.setAnimalType(animalType);
        searchParams.setAnimalIdentifier(animalIdentifier);

        return searchParams;
    }

    public static SearchForAnimalParams returnPageableParams(Integer number, Integer size) {
        SearchForAnimalParams searchParams = new SearchForAnimalParams();
        searchParams.setNumber(number);
        searchParams.setSize(size);

        return searchParams;
    }

    public static Pageable returnPageable(Integer number, Integer size) {
        return new PageRequest(number, size);
    }

    @Test
    public void shouldCreatePagination() {
        Pageable data = SearchForAnimalParams.createPagination(SECOND_PAGE, ONE_ENTITY);

        assertEquals(SECOND_PAGE, data.getPageNumber());
        assertEquals(ONE_ENTITY, data.getPageSize());
    }

    @Test
    public void shouldCreateDefaultPagination() {
        Pageable data = SearchForAnimalParams.createPagination(null, null);

        assertEquals(FIRST_PAGE, data.getPageNumber());
        assertEquals(DEFAULT_PAGE_SIZE, data.getPageSize());
    }
}