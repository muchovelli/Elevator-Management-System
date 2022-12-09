package com.adam.elevatormanagementsystem.services;

import com.adam.elevatormanagementsystem.models.EDirection;
import com.adam.elevatormanagementsystem.models.Elevator;
import com.adam.elevatormanagementsystem.models.State;
import com.adam.elevatormanagementsystem.models.ThreadPoolExecutorUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Adam
 * @created 2020/05/18
 */
@Service
public class ElevatorServiceImpl implements ElevatorService {

    private final ThreadPoolExecutorUtil threadPoolExecutorUtil;

    private final int MAX_FLOOR = 16;
    private final int MIN_FLOOR = 0;

    private List<Thread> threads = new ArrayList<>();

    public List<Elevator> elevators = new ArrayList<>();

    /**
     * @param threadPoolExecutorUtil
     */
    public ElevatorServiceImpl(ThreadPoolExecutorUtil threadPoolExecutorUtil) {
        this.threadPoolExecutorUtil = threadPoolExecutorUtil;
    }

    /**
     * @param elevator
     */
    @Override
    public void save(Elevator elevator) {
        elevators.add(elevator);
    }

    /**
     * @return all elevators
     */
    @Override
    public List<Elevator> findAll() {
        return elevators;
    }

    /**
     * @param elevator
     * @param startingFloor
     * @param targetFloor
     * @return EDiretion
     * @throws InterruptedException
     */
    private EDirection getDirection(Elevator elevator, final int startingFloor, final int targetFloor) throws IllegalArgumentException {
        elevator.setTargetFloor(targetFloor);

        if (startingFloor > elevator.getTargetFloor()) {
            return EDirection.DOWN;
        }
        if (startingFloor < elevator.getTargetFloor()) {
            return EDirection.UP;
        }

        throw new IllegalArgumentException("Invalid floor, are you drunk? :D");
    }

    /**
     * @param thread
     * @param elevator
     * @throws InterruptedException
     */
    public void startMoving(Thread thread, Elevator elevator) throws InterruptedException, IOException {
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

        elevator.setDirection(EDirection.STOP);
        elevator.setState(State.IDLE);
        getElevatorsStatus();
        thread.stop();
        getElevatorsStatus();
    }

    /**
     * @throws InterruptedException, IOException
     */
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

        System.out.println("\n\n");

        for (int i = 0; i < threads.size(); i++) {
            if (threads.get(i).isAlive()) {
                System.out.println("Thread " + i + " is " + threads.get(i).getState());
            }
        }
    }

    /**
     * @param startingFloor - floor where you want to get in
     * @param targetFloor   - the floor where the elevator should go
     * @throws InterruptedException
     */
    @Override
    public void orderElevator(final int startingFloor, final int targetFloor) throws IllegalArgumentException {
        if (startingFloor > MAX_FLOOR || startingFloor < MIN_FLOOR || targetFloor > MAX_FLOOR || targetFloor < MIN_FLOOR) {
            throw new IllegalArgumentException("Invalid floor");
        }

        Elevator elevator = findFreeElevator(startingFloor);

        if (elevator == null) {
            throw new IllegalArgumentException("No free elevator\nTry again later");
        }

        Thread t1 = new Thread();
        Thread finalT1 = t1;
        t1 = new Thread(() -> {
            try {
                if (elevator.getFloor() != startingFloor) {
                    elevator.setTargetFloor(startingFloor);
                    Thread finalT = finalT1;
                    startMoving(finalT, elevator);
                }
                elevator.setTargetFloor(targetFloor);
                startMoving(finalT1, elevator);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        threads.add(t1);
        t1.start();
    }

    /**
     * @param startingFloor - floor where you want to get in
     * @return Elevator
     */
    public Elevator findFreeElevator(final int startingFloor) {
        Elevator elevator = new Elevator();

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

    /**
     * @param elevator - elevator which is moving
     * @throws InterruptedException
     */
    private void move(Elevator elevator) throws InterruptedException, IOException {
        elevator.setState(State.MOVING);
        Thread.sleep(5000);
        getElevatorsStatus();
        if (elevator.getDirection() == EDirection.UP) {
            elevator.setFloor(elevator.getFloor() + 1);
        } else if (elevator.getDirection() == EDirection.DOWN) {
            elevator.setFloor(elevator.getFloor() - 1);
        }
    }
}

