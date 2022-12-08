package com.adam.elevatormanagementsystem.models;

import com.adam.elevatormanagementsystem.services.ElevatorService;

import java.util.List;

public class TaskThread implements Runnable {
    private final ElevatorService elevatorService;
    public List<Elevator> elevators;

    public TaskThread() {
        elevatorService = null;
    }

    public TaskThread(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @Override
    public void run() {
        elevators = elevatorService.findAll();
        System.out.println("Thread Name: " + Thread.currentThread().getName());

    }
}
