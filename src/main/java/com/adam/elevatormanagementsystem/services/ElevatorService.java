package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.Elevator;

import java.util.List;
import java.util.stream.Stream;

public interface ElevatorService {
    void save(Elevator elevator);

    List<Elevator> findAll();

    Elevator findById(Long id);

    Stream<Elevator> findByFloor(int floor);
}
