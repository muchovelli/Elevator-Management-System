package com.adam.elevatormanagementsystem.controllers;

import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    @Autowired
    private ElevatorService elevatorService;

    /**
     * Save the elevator
     *
     * @param elevator
     */
    @PostMapping("/save")
    public void saveElevator(@ModelAttribute("Invoice") Elevator elevator) {
        elevatorService.save(elevator);
    }


    /**
     * Get all elevators
     *
     * @return List of elevators
     */
    @GetMapping("/findAllElevators")
    public List<Elevator> getElevators() {
        return elevatorService.findAll();
    }

    /**
     * Order an elevator
     *
     * @param startingFloor
     * @param targetFloor
     * @throws InterruptedException
     */
    @PostMapping("/orderElevator/{startingFloor}&{targetFloor}")
    public void orderElevator(@PathVariable int startingFloor, @PathVariable int targetFloor) throws InterruptedException {
        elevatorService.orderElevator(startingFloor, targetFloor);
    }

    /**
     * Get elevators status
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/status")
    public void status() throws IOException, InterruptedException {
        elevatorService.getElevatorsStatus();
    }

}
