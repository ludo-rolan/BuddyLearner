package com.example.buddylearner.data.model;

import androidx.annotation.Nullable;

import com.example.buddylearner.data.enums.UserRole;

public class User {

    @Nullable
    private String userId;
    private String userName;
    @Nullable
    private String email;
    private String password;
    private String role;

    public User () {}

    // constructor for log in
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // constructor for registration
    public User(String userName, String email, String password, UserRole role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = String.valueOf(role.learner);
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

    public void setUserName() {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword() { this.password = password; }

    public String getRole() { return role; }

    public void setRole() { this.role = role; }

}
