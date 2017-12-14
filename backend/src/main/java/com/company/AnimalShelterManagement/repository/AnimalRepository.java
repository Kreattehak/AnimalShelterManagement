package com.company.AnimalShelterManagement.repository;

import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE'")
    Page<Animal> findAnimalsByAvailableForAdoption(Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE' AND a.type = ?1 "
            + "AND a.name LIKE CONCAT('%',?2,'%')")
    Page<Animal>findAnimalsAvailableForAdoptionByName(Animal.Type animalType, String animalName, Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE' AND a.type = ?1"
            + " AND a.animalIdentifier LIKE CONCAT('%',?2,'%')")
    Page<Animal> findAnimalsAvailableForAdoptionByIdentifier(Animal.Type animalType, String animalIdentifier,
                                                                 Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption <> 'ADOPTED' ORDER BY a.dateOfRegistration ASC")
    Page<Animal> findNotAdoptedAnimals(Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption <> 'ADOPTED' AND a.type = ?1 "
            + "AND a.name LIKE CONCAT('%',?2,'%')")
    Page<Animal> findNotAdoptedAnimalsByName(Animal.Type animalType, String animalName, Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption <> 'ADOPTED' AND a.type = ?1"
            + " AND a.animalIdentifier LIKE CONCAT('%',?2,'%')")
    Page<Animal> findNotAdoptedAnimalsByIdentifier(Animal.Type animalType, String animalIdentifier, Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption = 'AVAILABLE' ORDER BY a.dateOfRegistration DESC")
    Page<Animal> findAnimalsWithLongestWaitingTime(Pageable pageable);

    @Query("SELECT a FROM Animal a WHERE a.availableForAdoption <> 'ADOPTED' ORDER BY a.dateOfRegistration DESC")
    Page<Animal> findRecentlyAddedAnimals(Pageable pageable);

    @Query("SELECT p.animal FROM Person p WHERE p.id = ?1")
    Iterable<Animal> findAnimalsOwnedByPerson(Long personId);

    @Query("SELECT a.previousOwner FROM Animal a WHERE a.id = ?1")
    Person findPersonByAnimalId(Long animalId);

    @Query("SELECT COUNT(p) FROM Person p JOIN p.animal GROUP BY p.id")
    long[] findAnimalsCountForPeople();
}