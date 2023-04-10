package com.example.becarefall;

import java.io.Serializable;

public class c_User implements Serializable {
    private String userID;
    private String accountID;
    private String name;
    private String phoneNumber;
    private String status;
    private String address;

    public c_User(String userID, String account_ID, String name,  String phone_Number, String status, String address) {
        this.userID = userID;
        this.accountID = account_ID;
        this.name = name;
        this.phoneNumber = phone_Number;
        this.status = status;
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
