package com.adam.elevatormanagementsystem.controllers;

import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/elevator")
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
    public Elevator getElevatorById(@PathVariable Long id) {
        return elevatorService.findById(id);
    }

    @GetMapping("/findElevatorByFloor/{floor}")
    public Stream<Elevator> getElevatorByFloor(@PathVariable int floor) {
        return elevatorService.findByFloor(floor);
    }

}
