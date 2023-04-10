package com.example.becarefall;

public class c_ContactRecord {
    private String contactRecordID;
    private String fallRecordID;
    private String userID;
    private String name;
    private String phone;
    private String time;
    private String pickUp;

    public c_ContactRecord(String contactRecordID, String fallRecordID, String userID, String name, String phone, String time, String pickUp) {
        this.contactRecordID = contactRecordID;
        this.fallRecordID = fallRecordID;
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.pickUp = pickUp;
    }

    public String getContactRecordID() {
        return contactRecordID;
    }

    public void setContactRecordID(String contactRecordID) {
        this.contactRecordID = contactRecordID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }
}
