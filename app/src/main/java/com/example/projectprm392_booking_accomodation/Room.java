package com.example.projectprm392_booking_accomodation;

public class Room {
    private int roomId;
    private int accomodationId;
    private String roomName;
    private int status;
    private int capacity;
    private double price;
    private String description;

    public Room() {
    }

    public Room(int roomId, int accomodationId, String roomName, int status, int capacity, double price, String description) {
        this.roomId = roomId;
        this.accomodationId = accomodationId;
        this.roomName = roomName;
        this.status = status;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getAccomodationId() {
        return accomodationId;
    }

    public void setAccomodationId(int accomodationId) {
        this.accomodationId = accomodationId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
