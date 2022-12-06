package com.adam.elevatormanagementsystem.models;

public class ElevatorRequest {
    private int destinationFloor;
    private EDirection direction;

    public ElevatorRequest() {
    }

    public ElevatorRequest(int destinationFloor, EDirection direction) {
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public EDirection getDirection() {
        return direction;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }
}
