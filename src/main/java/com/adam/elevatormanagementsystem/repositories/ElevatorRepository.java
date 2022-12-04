package com.adam.elevatormanagementsystem.repositories;

import com.adam.elevatormanagementsystem.models.Elevator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorRepository extends MongoRepository<Elevator, Long> {

}

