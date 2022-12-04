package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.repositories.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ElevatorServiceImpl implements ElevatorService {

    @Autowired
    ElevatorRepository elevatorRepository;

    @Override
    public void save(Elevator elevator) {
        elevatorRepository.insert(elevator);
    }

    @Override
    public List<Elevator> findAll() {
        return elevatorRepository.findAll();
    }

    @Override
    public Elevator findById(Long id) {
        return elevatorRepository.findAll().stream().filter(elevator -> elevator.getId().equals(id)).findFirst().get();
    }

    @Override
    public Stream<Elevator> findByFloor(int floor) {
        return elevatorRepository.findAll().stream().filter(elevator -> elevator.getFloor() == floor);
    }

}

