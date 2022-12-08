package com.adam.elevatormanagementsystem.controllers;

import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    @Autowired
    private ElevatorService elevatorService;

    @PostMapping("/save")
    public void saveElevator(@ModelAttribute("Invoice") Elevator elevator) {
        elevatorService.save(elevator);
    }

    @GetMapping("/findAllElevators")
    public List<Elevator> getElevators() {
        return elevatorService.findAll();
    }

    @GetMapping("/findElevatorById/{id}")
    public Optional<Elevator> getElevatorById(@PathVariable Long id) {
        return elevatorService.findById(id);
    }

    @GetMapping("/findElevatorByFloor/{floor}")
    public Stream<Elevator> getElevatorByFloor(@PathVariable int floor) {
        return elevatorService.findByFloor(floor);
    }

    @GetMapping("/getElevatorsAsync")
    public List<Elevator> getAllEleavtorsAsync() throws IOException, InterruptedException {
        return elevatorService.getElevatorsAsync();
    }

    @PostMapping("/orderElevator/{startingFloor}&{targetFloor}")
    public void orderElevator(@PathVariable int startingFloor, @PathVariable int targetFloor) throws InterruptedException {
        elevatorService.orderElevator(startingFloor, targetFloor);
    }

    @GetMapping("/findIdlingElevator/{startingFloor}&{targetFloor}")
    public void findFreeElevator(@PathVariable int startingFloor, @PathVariable int targetFloor) {
        elevatorService.findFreeElevator(startingFloor);
    }

    @GetMapping("/status")
    public void status() throws IOException, InterruptedException {
        elevatorService.getElevatorsStatus();
    }

}
