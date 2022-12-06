package com.adam.elevatormanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HistoryEntry {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Long elevatorId;
    private int fromFloor;
    private int toFloor;
    private int numOfPassengers;

    public HistoryEntry() {
    }

    public HistoryEntry(Long id, Long elevatorId, int fromFloor, int toFloor, int numOfPassengers) {
        this.id = id;
        this.elevatorId = elevatorId;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.numOfPassengers = numOfPassengers;
    }

    public Long getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Long elevatorId) {
        this.elevatorId = elevatorId;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public void setToFloor(int toFloor) {
        this.toFloor = toFloor;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
