package com.company.AnimalShelterManagement;

import com.company.AnimalShelterManagement.model.Address;
import com.company.AnimalShelterManagement.model.Animal;
import com.company.AnimalShelterManagement.model.Bird;
import com.company.AnimalShelterManagement.model.Cat;
import com.company.AnimalShelterManagement.model.Dog;
import com.company.AnimalShelterManagement.model.Person;
import com.company.AnimalShelterManagement.repository.PersonRepository;
import com.company.AnimalShelterManagement.service.interfaces.AddressService;
import com.company.AnimalShelterManagement.service.interfaces.AnimalService;
import com.company.AnimalShelterManagement.service.interfaces.BirdService;
import com.company.AnimalShelterManagement.service.interfaces.CatService;
import com.company.AnimalShelterManagement.service.interfaces.DogService;
import com.company.AnimalShelterManagement.service.interfaces.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public CommandLineRunner demo() {
        return (args) -> {
            //data is loaded to db from data.sql
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
