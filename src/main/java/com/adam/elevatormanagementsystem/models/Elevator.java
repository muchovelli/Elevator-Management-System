package com.adam.elevatormanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Elevator {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "current_floor", nullable = false)
    private int floor;

    @Column(name = "direction")
    private EDirection direction = EDirection.STOP;

    private int targetFloor;

    @Column(name = "state")
    private State state = State.IDLE;

    @Column(name = "numOfPassengers")
    private int numOfPassengers = 0;

    public Elevator() {
    }

    public Elevator(Long id, int floor, EDirection direction, int targetFloor, State state, int numOfPassengers) {
        this.id = id;
        this.floor = floor;
        this.direction = direction;
        this.targetFloor = targetFloor;
        this.state = state;
        this.numOfPassengers = numOfPassengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public EDirection getDirection() {
        return direction;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }
    
}
