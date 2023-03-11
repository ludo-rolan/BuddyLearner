package com.example.buddylearner.ui.login;

public class LoggedInUserView {

    private String username;
    private String password;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUserName() {
        return username;
    }

    String getPassword() { return password; }

}
