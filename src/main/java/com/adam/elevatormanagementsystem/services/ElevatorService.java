package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.Elevator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ElevatorService {
    void save(Elevator elevator);

    List<Elevator> findAll();

    Optional<Elevator> findById(Long id);

    Stream<Elevator> findByFloor(int floor);

    List<Elevator> getElevatorsAsync() throws IOException, InterruptedException;

    void orderElevator(int startingFloor, int targetFloor) throws InterruptedException;

    Elevator findFreeElevator(int startingFloor);

    void startMoving(Elevator elevator) throws InterruptedException, IOException;

    void getElevatorsStatus() throws IOException, InterruptedException;
}
