package com.example.becarefall;

import java.io.Serializable;

public class c_Account implements Serializable {
    private String accountID;
    private String username;
    private String password;

    public c_Account(String account_ID, String username, String password) {
        this.accountID = account_ID;
        this.username = username;
        this.password = password;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
