package com.company.AnimalShelterManagement.controller;

import com.company.AnimalShelterManagement.model.Bird;
import com.company.AnimalShelterManagement.service.interfaces.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class BirdController {

    private final BirdService birdService;

    @Autowired
    public BirdController(BirdService birdService) {
        this.birdService = birdService;
    }

    @GetMapping(value = "${rest.bird.getBird}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Bird returnBird(@PathVariable Long birdId) {
        return birdService.returnBird(birdId);
    }

    @PostMapping(value = "${rest.bird.postBird}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Bird saveBird(@Valid @RequestBody Bird bird) {
        return birdService.saveBird(bird);
    }

    @PutMapping(value = "${rest.bird.putBird}", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public Bird updateBird(@Valid @RequestBody Bird bird) {
        return birdService.updateBird(bird);
    }

    @DeleteMapping("${rest.bird.deleteBird}")
    public void deleteBird(@PathVariable Long birdId) {
        birdService.deleteBird(birdId);
    }
}