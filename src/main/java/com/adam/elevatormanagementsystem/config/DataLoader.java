package com.adam.elevatormanagementsystem.config;

import com.adam.elevatormanagementsystem.models.EDirection;
import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.models.State;
import com.adam.elevatormanagementsystem.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ElevatorService elevatorService;

    public DataLoader(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for(int i = 1; i <= 16; i++) {
            Elevator elevator = new Elevator((long) i, 0, EDirection.STOP,0, State.IDLE, 0);
            elevatorService.save(elevator);
            System.out.println(elevator);
            System.out.println("Elevator " + elevator.getId() + " is on floor " + elevator.getFloor());
        }
    }
}
