package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        for (int i = 0; i < MAX_ELEVATORS; i++) {
            TaskThread taskThread = new TaskThread();
            threadPoolExecutorUtil.executeTask(taskThread);
            Elevator elevator = new Elevator();
            elevator.setFloor(1);
            save(elevator);
            System.out.println("Elevator " + elevator.getId() + " is on floor " + elevator.getFloor());
        }
        TaskThread taskThread = new TaskThread(this);
        threadPoolExecutorUtil.executeTask(taskThread);
        return taskThread.elevators;
    }


    private EDirection getDirection(Elevator elevator, int startingFloor, int targetFloor) {
        elevator.setTargetFloor(targetFloor);
        if (startingFloor > elevator.getTargetFloor()) {
            return EDirection.DOWN;
        } else if (startingFloor < elevator.getTargetFloor()) {
            return EDirection.UP;
        }
        System.out.println("Are you drunk? ;)");
        return EDirection.STOP;
    }

    public void startMoving(Elevator elevator) throws InterruptedException, IOException {
        if (elevator.getTargetFloor() > elevator.getFloor()) {
            elevator.setDirection(EDirection.UP);
            while (elevator.getFloor() < elevator.getTargetFloor()) {
                move(elevator);
            }
        } else if (elevator.getTargetFloor() < elevator.getFloor()) {
            elevator.setDirection(EDirection.DOWN);
            while (elevator.getFloor() > elevator.getTargetFloor()) {
                move(elevator);
            }
        }

        System.out.println("Elevator " + elevator.getId() + " stopped at floor " + elevator.getFloor());
        elevator.setDirection(EDirection.STOP);
        elevator.setState(State.IDLE);
        getElevatorsStatus();

    }


    public void getElevatorsStatus() throws IOException, InterruptedException {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").start().waitFor();
        } else {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }

        for (Elevator elevator : findAll()) {
            System.out.println("ID: " + elevator.getId() + "\t| Floor: " + elevator.getFloor() + "\t| state: " + elevator.getState() + "\t| direction: " + elevator.getDirection() + "\t| target floor: " + elevator.getTargetFloor());

        }
    }

    //TODO

    @Override
    public void orderElevator(int startingFloor, int targetFloor) {
        if (startingFloor > MAX_FLOOR || startingFloor < MIN_FLOOR || targetFloor > MAX_FLOOR || targetFloor < MIN_FLOOR) {
            System.out.println("Invalid floor");
            return;
        }

        Elevator elevator = findFreeElevator(startingFloor);

        if (elevator == null) {
            System.out.println("No free elevator\nTry again later");
            return;
        }


        //System.out.println("Found elevator " + elevator.getId() + " on floor " + elevator.getFloor() + " going to floor " + targetFloor + " State: " + elevator.getState());
        Thread t1 = new Thread(() -> {
            try {
                //System.out.println("Elevator started at thread " + Thread.currentThread().getName());
                if (elevator.getFloor() != startingFloor) {
                    elevator.setTargetFloor(startingFloor);
                    startMoving(elevator);
                }
                elevator.setTargetFloor(targetFloor);
                startMoving(elevator);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Thread " + t1.getName() + " is running");
        t1.start();
    }

    public Elevator findFreeElevator(int startingFloor) {
        Elevator elevator = new Elevator();

        // Finding the closest elevator
        HashMap<Elevator, Integer> elevatorsDistance = new HashMap<>();
        for (Elevator e : findAll()) {
            if (e.getState() == State.IDLE && e.getDirection() == EDirection.STOP) {
                int distance = Math.abs(e.getFloor() - startingFloor);
                elevatorsDistance.put(e, distance);
            }
        }
        if (elevatorsDistance.isEmpty()) {
            return null;
        }
        elevator = Collections.min(elevatorsDistance.entrySet(), Map.Entry.comparingByValue()).getKey();

        return elevator;
    }

    private void move(Elevator elevator) throws InterruptedException, IOException {
        elevator.setState(State.MOVING);
        Thread.sleep(5000);
        getElevatorsStatus();
        elevator.setFloor(elevator.getFloor() + 1);
    }
}

