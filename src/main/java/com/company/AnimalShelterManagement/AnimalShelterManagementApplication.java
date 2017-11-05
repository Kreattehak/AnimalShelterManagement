package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.AddressRepository;
import com.company.AnimalShelterManagement.repository.DogRepository;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:mappings.properties")
public class AnimalShelterManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalShelterManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PersonRepository personRepository, AddressRepository addressRepository,
                                  DogRepository dogRepository) {
        return (args) -> {
            Person person = new Person("Dany", "Devito");
            Address address = new Address("West Side Street", "Czikago", "40-400");
            Dog dog = new Dog("Pimpek", Animal.AnimalType.DOG, LocalDate.of(2010, 11, 6),
                    person, Dog.DogRace.CROSSBREAD);
            person.addAddress(address);

            personRepository.save(person);
            addressRepository.save(address);
            dog = dogRepository.save(dog);
            System.out.println(dog.generateIdentifier());
            dogRepository.save(dog);
        };
    }
}
