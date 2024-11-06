package com.example.contact;

public class CallLog {
    private int id;
    private String name;
    private String phoneNumber;
    private String callDay;
    private String callTime;
    private String callDate;
    private String callType;  // Incoming, Outgoing, Missed
    private String duration;   // in minutes

    public CallLog() {}

    public CallLog(int id, String name, String phoneNumber, String callDay, String callTime, String callDate, String callType, String duration) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callDay = callDay;
        this.callTime = callTime;
        this.callDate = callDate;
        this.callType = callType;
        this.duration = duration;
    }

    public CallLog(String name, String phoneNumber, String callDay, String callTime, String callDate, String callType, String duration) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.callDay = callDay;
        this.callTime = callTime;
        this.callDate = callDate;
        this.callType = callType;
        this.duration = duration;
    }

    public CallLog(String phoneNumber, String callDay, String callTime, String callDate, String callType, String duration) {
        this.name = "Unknown";
        this.phoneNumber = phoneNumber;
        this.callDay = callDay;
        this.callTime = callTime;
        this.callDate = callDate;
        this.callType = callType;
        this.duration = duration;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCallDay() { return callDay; }
    public void setCallDay(String callDay) { this.callDay = callDay; }

    public String getCallTime() { return callTime; }
    public void setCallTime(String callTime) { this.callTime = callTime; }

    public String getCallDate() { return callDate; }
    public void setCallDate(String callDate) { this.callDate = callDate; }

    public String getCallType() { return callType; }
    public void setCallType(String callType) { this.callType = callType; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "Call Log: " + id + "\nPhone: " + phoneNumber + "\nDay: " + callDay +
                "\nTime: " + callTime + "\nDate: " + callDate + "\nType: " + callType +
                "\nDuration: " + duration + " minutes";
    }
}
