package com.company.AnimalShelterManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @ManyToOne
    @JoinTable(name = "person_address", joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIgnore
    private Person person;

    @Column(name = "street_name", nullable = false, length = 50)
    @Length(min = 3, message = "{validation.minLength}")
    @NotNull
    private String streetName;

    @Column(name = "city_name", nullable = false, length = 25)
    @Length(min = 3, message = "{validation.minLength}")
    @NotNull
    private String cityName;

    @Column(name = "zip_code", nullable = false, length = 6)
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "{validation.zipCodePattern}")
    @NotNull
    private String zipCode;

    public Address() {
    }

    public Address(String streetName, String cityName, String zipCode) {
        this.streetName = streetName;
        this.cityName = cityName;
        this.zipCode = zipCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + super.getId() +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
