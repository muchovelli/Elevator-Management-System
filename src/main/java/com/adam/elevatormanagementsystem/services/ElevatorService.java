package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.Elevator;

import java.io.IOException;
import java.util.List;

public interface ElevatorService {
    void save(Elevator elevator);

    List<Elevator> findAll();
    
    void orderElevator(int startingFloor, int targetFloor) throws InterruptedException;

    Elevator findFreeElevator(int startingFloor);

    void startMoving(Thread thread, Elevator elevator) throws InterruptedException, IOException;

    void getElevatorsStatus() throws IOException, InterruptedException;
}
