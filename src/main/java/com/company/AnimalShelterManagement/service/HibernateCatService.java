package com.company.AnimalShelterManagement.service;

import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.repository.CatRepository;
import com.company.AnimalShelterManagement.service.interfaces.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HibernateCatService implements CatService {

    private final CatRepository catRepository;

    @Autowired
    public HibernateCatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Cat> returnCats() {
        return catRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cat returnCat(Long catId) {
        return catRepository.findOne(catId);
    }

    @Override
    public Cat saveCat(Cat cat) {
        cat = catRepository.save(cat);
//        super.generateIdentifier(cat);

        return cat;
    }

    @Override
    public Cat updateCat(Cat cat) {
        return catRepository.save(cat);
    }

    @Override
    public void deleteCat(Long catId) {
        catRepository.delete(catId);
    }
}