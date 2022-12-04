package com.adam.elevatormanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ElevatorManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElevatorManagementSystemApplication.class, args);
    }

}
