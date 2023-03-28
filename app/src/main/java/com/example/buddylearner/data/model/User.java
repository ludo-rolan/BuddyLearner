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

    // constructor for following topic
    public User(String userId, String userName, String email, UserRole role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role.name().equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner.name() : UserRole.tutor.name();;
    }

    // constructor for registration & retrieving all users in firestore
    public User(String userId, String userName, String email, String password, UserRole role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role.name().equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner.name() : UserRole.tutor.name();
        //this.role = String.valueOf(role.learner);
    }

    // constructor for retrieving all users in firestore
//    public User(String userId, String userName, String email, String password) {
//        this.userId = userId;
//        this.userName = userName;
//        this.email = email;
//        this.password = password;
//    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

}
