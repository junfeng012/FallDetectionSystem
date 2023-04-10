package com.example.becarefall;

public class c_FallRecord {

    private String fallRecordID;
    private String userID;
    private String date;
    private String time;
    private String emergencyCall;


    public c_FallRecord(String fallRecordID, String userID, String date,String time,String emergencyCall) {
        this.fallRecordID = fallRecordID;
        this.userID = userID;
        this.date = date;
        this.time = time;
        this.emergencyCall = emergencyCall;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFallRecordID() {
        return fallRecordID;
    }

    public void setFallRecordID(String fallRecordID) {
        this.fallRecordID = fallRecordID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmergencyCall() {
        return emergencyCall;
    }

    public void setEmergencyCall(String emergencyCall) {
        this.emergencyCall = emergencyCall;
    }
}
