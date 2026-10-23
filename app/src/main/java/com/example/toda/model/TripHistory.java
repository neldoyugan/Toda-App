package com.example.toda.model;

public class TripHistory {

    private String destination;
    private String fare;
    private String date;

    public TripHistory(String destination, String fare, String date) {
        this.destination = destination;
        this.fare = fare;
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public String getFare() {
        return fare;
    }

    public String getDate() {
        return date;
    }
}
