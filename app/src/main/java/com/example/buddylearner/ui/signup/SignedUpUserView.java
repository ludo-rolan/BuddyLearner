package com.example.buddylearner.ui.signup;

import java.io.Serializable;

public class SignedUpUserView implements Serializable {

    private String username;
    private String email;
    private String password;
    //... other data fields that may be accessible to the UI

    SignedUpUserView(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;

    }

    String getUsername() {
        return username;
    }

    String getEmail() {
        return email;
    }

    String getPassword() { return password; }

}
