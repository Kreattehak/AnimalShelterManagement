package com.company.AnimalShelterManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "animal")
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal extends BaseEntity {

    @Column(name = "name", nullable = false, length = 25)
    @Length(min = 3, message = "{validation.minLength}")
    @NotNull
    private String name;

    @Column(name = "type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    @Column(name = "behavior_description", length = 500)
    @Length(min = 3, message = "{validation.minLength}")
    private String behaviorDescription;

    @Column(name = "available_for_adoption", nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AvailableForAdoption availableForAdoption;

    @Column(name = "animal_identifier", length = 8, unique = true)
    @Pattern(regexp = "\\d{8}", message = "{validation.animalIdentifierPattern}")
    /*first two letters - animal type, second two letters - year of birth, third four letters - unique id
    animalIdentifier is print on dog/cat id tag*/
    private String animalIdentifier;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfBirth;

    @Column(name = "date_of_registration", nullable = false, updatable = false, columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfRegistration;

    @ManyToOne
    @JoinTable(name = "person_animal", joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIgnore
    private Person previousOwner;

    Animal() {
        this.dateOfRegistration = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public AvailableForAdoption getAvailableForAdoption() {
        return availableForAdoption;
    }

    public void setAvailableForAdoption(AvailableForAdoption availableForAdoption) {
        this.availableForAdoption = availableForAdoption;
    }

    public String getAnimalIdentifier() {
        return animalIdentifier;
    }

    public void setAnimalIdentifier(String animalIdentifier) {
        if (this.animalIdentifier == null) {
            this.animalIdentifier = animalIdentifier;
        }
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Person getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Person previousOwner) {
        this.previousOwner = previousOwner;
    }

    public enum Type {
        DOG("00"),
        CAT("01"),
        BIRD("02");

        private String typeIdentifier;

        Type(String typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
        }

        public String getTypeIdentifier() {
            return typeIdentifier;
        }
    }

    public enum AvailableForAdoption {
        AVAILABLE(true),
        UNDER_VETERINARY_CARE(false),
        ADOPTED(false),
        BEFORE_VACCINATION(false);

        private boolean isAvailable;

        AvailableForAdoption(boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public boolean isAvailable() {
            return isAvailable;
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", behaviorDescription='" + behaviorDescription + '\'' +
                ", availableForAdoption=" + availableForAdoption +
                ", animalIdentifier='" + animalIdentifier + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}

