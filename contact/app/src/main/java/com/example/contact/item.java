package com.example.contact;

public class item {
    String name;
    String phone;
    String initial;
    String date;
    String duration;
    String day;
    String time;
    //String call_type;
    int image;

    public item(String name, String phone, String initial, String date, String duration, String day, String time) {
        this.name = name;
        this.phone = phone;
        this.initial = initial;
        this.date = date;
        this.duration = duration;
        this.day = day;
        this.time = time;
        //this.call_type = call_type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getInitial() {
        return initial;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

   // public String getCall_type() {
   //     return call_type;
   // }
}
