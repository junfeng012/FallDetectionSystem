package com.example.becarefall;

import java.io.Serializable;

public class c_Caretaker implements Serializable {
    private String caretakerID;
    private String accountID;
    private String userID;
    private String name;
    private String email;
    private String phone;
    private String availability;
    private String priority;

    public c_Caretaker(String caretakerID, String accountID, String userID, String name,String email, String phone, String availability, String priority) {
        this.caretakerID = caretakerID;
        this.accountID = accountID;
        this.userID = userID;
        this.name = name;
        this.email=email;
        this.phone = phone;
        this.availability = availability;
        this.priority = priority;
    }

    public String getCaretakerID() {
        return caretakerID;
    }

    public void setCaretakerID(String caretakerID) {
        this.caretakerID = caretakerID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
