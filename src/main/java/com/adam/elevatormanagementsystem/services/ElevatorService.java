package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.Elevator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ElevatorService {
    void save(Elevator elevator);
    List<Elevator> findAll();
    Optional<Elevator> findById(Long id);
    Stream<Elevator> findByFloor(int floor);
    List<Elevator> getElevatorsAsync();
    void orderElevator(int startingFloor, int targetFloor) throws InterruptedException;
    Elevator findFreeElevator(int startingFloor, int targetFloor);
}
