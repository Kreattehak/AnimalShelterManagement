package com.company.AnimalShelterManagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
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
