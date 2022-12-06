package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    private final ThreadPoolExecutorUtil threadPoolExecutorUtil;

    private final int MAX_FLOOR = 16;
    private final int MIN_FLOOR = 0;
    private final int MAX_ELEVATORS = 16;

    public List<Elevator> elevators = new ArrayList<>();

    public ElevatorServiceImpl(ThreadPoolExecutorUtil threadPoolExecutorUtil) {
        this.threadPoolExecutorUtil = threadPoolExecutorUtil;
    }

    @Override
    public void save(Elevator elevator) {
        elevators.add(elevator);
    }

    @Override
    public List<Elevator> findAll() {
        return elevators;
    }

    @Override
    public Optional<Elevator> findById(Long id) {
        return Optional.ofNullable(elevators.stream().filter(elevator -> elevator.getId() == id).findFirst().orElse(null));
    }

    //TODO
    @Override
    public Stream<Elevator> findByFloor(int floor) {
        return null;
    }

    @Override
    public List<Elevator> getElevatorsAsync() {
        for (int i=0;i < MAX_ELEVATORS;i++){
            TaskThread taskThread = new TaskThread();
            threadPoolExecutorUtil.executeTask(taskThread);
            Elevator elevator = new Elevator();
            elevator.setFloor(1);
            save(elevator);
            System.out.println("Elevator " + elevator.getId() + " is on floor " + elevator.getFloor());
        }
        TaskThread taskThread=new TaskThread(this);
        threadPoolExecutorUtil.executeTask(taskThread);
        return taskThread.elevators;
    }


    public Elevator findFreeElevator(int startingFloor, int targetFloor){
        Elevator elevator;
        HashMap<Elevator, Integer> elevatorDistance = new HashMap<>();
        for(Elevator e : findAll()){
            if(e.getState() == State.IDLE && e.getDirection() == EDirection.STOP ){
                int distance = Math.abs(e.getFloor() - targetFloor);
                elevatorDistance.put(e, distance);
            }
        }
        elevator = Collections.min(elevatorDistance.entrySet(), Map.Entry.comparingByValue()).getKey();

        return elevator;
    }

    @Override
    public void orderElevator(int startingFloor, int targetFloor){

        if(startingFloor > MAX_FLOOR || startingFloor < MIN_FLOOR || targetFloor > MAX_FLOOR || targetFloor < MIN_FLOOR) {
            System.out.println("Invalid floor");
        }

        Elevator elevator = findFreeElevator(startingFloor, targetFloor);
        System.out.println("Found elevator " + elevator.getId() + " on floor " + elevator.getFloor() + " going to floor " + targetFloor + " State: " + elevator.getState());
        Thread t1 = new Thread(() -> {
            if (elevator != null) {
                elevator.setTargetFloor(targetFloor);
                try {
                    System.out.println("Elevator started at thread " + Thread.currentThread().getName());
                    elevator.move();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Thread " + t1.getName() + " is running");
        t1.start();
    }

}

