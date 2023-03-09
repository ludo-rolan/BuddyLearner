package com.example.buddylearner.ui.signup;

public class SignedUpUserView {

    private String username;
    private String password;
    //... other data fields that may be accessible to the UI

    SignedUpUserView(String username, String password) {

        this.username = username;
        this.password = password;

    }

    String getUsername() {
        return username;
    }

    String getPassword() { return password; }

}
