package com.example.toda.adapter;

public class Booking {
    private String pickup;
    private String dropoff;
    private String dateTime;
    private String fare;
    private String status;
    private long   timestamp;

    public Booking(String pickup, String dropoff, String dateTime, String fare, String status, long timestamp) {
        this.pickup    = pickup;
        this.dropoff   = dropoff;
        this.dateTime  = dateTime;
        this.fare      = fare;
        this.status    = status;
        this.timestamp = timestamp;
    }

    public String getPickup()    { return pickup; }
    public String getDropoff()   { return dropoff; }
    public String getDateTime()  { return dateTime; }
    public String getFare()      { return fare; }
    public String getStatus()    { return status; }
    public long   getTimestamp() { return timestamp; }
}
