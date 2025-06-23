package com.example.callreminder;

public class Reminder {
    public long id;
    public String phone;
    public long timestamp;
    public int status; // 0 active, 1 completed

    public Reminder(long id, String phone, long timestamp, int status) {
        this.id = id;
        this.phone = phone;
        this.timestamp = timestamp;
        this.status = status;
    }
}
