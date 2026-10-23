package com.example.toda.model;

public class RideRequest {
    public String pickup, destination, fare, passengerCount, timeEstimate, status;

    public RideRequest() {}

    public RideRequest(String pickup, String destination, String fare, String passengerCount, String timeEstimate, String status) {
        this.pickup = pickup;
        this.destination = destination;
        this.fare = fare;
        this.passengerCount = passengerCount;
        this.timeEstimate = timeEstimate;
        this.status = status;
    }
}
