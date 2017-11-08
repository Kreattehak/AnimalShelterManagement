package com.company.AnimalShelterManagement.model.dto;

public abstract class BaseDTO {

    private Long id;

    BaseDTO() {

    }

    BaseDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
