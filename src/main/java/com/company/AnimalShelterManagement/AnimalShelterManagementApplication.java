package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import com.company.AnimalShelterManagement.service.interfaces.CatService;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
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
    public CommandLineRunner demo(CatService catService, DogService dogService,
                                  PersonService personService, AddressService addressService) {
        return (args) -> {
            Person person = new Person("Dany", "Devito");
            Address address = new Address("West Side Street", "Czikago", "40-400");
            Dog dog = new Dog("Pimpek", Animal.Type.DOG, LocalDate.of(2010, 11, 6),
                    person, Dog.Race.CROSSBREAD);
            Cat cat = new Cat("Mruczek", Animal.Type.CAT, LocalDate.of(2010, 11, 6),
                    person, Cat.Race.PERSIAN);
            person.addAddress(address);

            personService.savePerson(person);
            addressService.saveAddress(address);

            cat = catService.saveCat(cat);
            dog = dogService.saveDog(dog);

            System.out.println(cat);
            System.out.println(dog);

        };
    }
}
