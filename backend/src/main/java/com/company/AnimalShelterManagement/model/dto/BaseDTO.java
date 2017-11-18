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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseDTO dto = (BaseDTO) o;

        return id != null ? id.equals(dto.id) : dto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
