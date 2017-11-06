package com.company.AnimalShelterManagement.service.interfaces;

import com.company.AnimalShelterManagement.model.Cat;

public interface CatService {

    Iterable<Cat> returnCats();

    Cat returnCat(Long catId);

    Cat saveCat(Cat cat);

    Cat updateCat(Cat cat);

    void deleteCat(Long catId);
}
