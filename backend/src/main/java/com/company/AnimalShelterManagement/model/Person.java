package com.company.AnimalShelterManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 30)
    @Length(min = 3, max = 30, message = "{validation.minLength}")
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @Length(min = 3, max = 50, message = "{validation.minLength}")
    @NotNull
    private String lastName;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Address> address;

    @Column(name = "date_of_registration", nullable = false, updatable = false, columnDefinition = "DATE")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfRegistration;

    @OneToOne
    @JoinTable(name = "person_main_address", joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Address mainAddress;

    @OneToMany(mappedBy = "previousOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Animal> animal;

    public Person() {
        this.address = new HashSet<>();
        this.animal = new HashSet<>();
        this.dateOfRegistration = LocalDate.now();
    }

    public Person(String firstName, String lastName) {
        this();
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

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }

    public void addAddress(Address address) {
        if (this.address.isEmpty()) {
            this.mainAddress = address;
        }
        this.address.add(address);
        address.setPerson(this);
    }

    public void removeAddress(Address address) {
        this.address.remove(address);
        address.setPerson(null);
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Set<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(Set<Animal> animal) {
        this.animal = animal;
    }

    public boolean addAnimal(Animal animal) {
        if(this.animal.add(animal)) {
            animal.setPreviousOwner(this);
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Animal animal) {
        if (this.animal.remove(animal)) {
            animal.setPreviousOwner(null);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + super.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", dateOfRegistration=" + dateOfRegistration +
                ", mainAddress=" + mainAddress +
                ", animal=" + animal +
                '}';
    }
}
