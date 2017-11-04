package com.company.AnimalShelterManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:mappings.properties")
public class AnimalShelterManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalShelterManagementApplication.class, args);
	}
}
