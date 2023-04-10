package com.example.becarefall;

public class c_Dashboard {


    private String userID;
    private String value;
    private String time;
    private String counter;

    public c_Dashboard(String userID, String value, String time, String counter) {
        this.userID = userID;
        this.value = value;
        this.time = time;
        this.counter = counter;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
}
