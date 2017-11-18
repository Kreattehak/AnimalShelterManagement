package com.company.AnimalShelterManagement.model.dto;

public class PersonDTO extends BaseDTO {

    private String firstName;
    private String lastName;

    public PersonDTO() {
    }

    public PersonDTO(Long personId, String firstName, String lastName) {
        super(personId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + super.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
