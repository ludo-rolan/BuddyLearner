package com.example.buddylearner.data.model;

import androidx.annotation.Nullable;

public class User {

    @Nullable
    private String userId;
    private String userName;
    @Nullable
    private String email;
    private String password;

    public User () {}

    // constructor for log in
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // constructor for registration
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // constructor for retrieving all users in firestore
    public User(String userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

}
