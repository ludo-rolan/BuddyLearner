package com.example.buddylearner.data.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class UserTopic implements Serializable {

    private String userName;
    private String userRole;
    private String topicName;
    private String topicCategory;
    private Timestamp date;
//    private User user;
//    private Topic topic;

    UserTopic() {}

//    public UserTopic(User user, Topic topic) {
//        this.user = user;
//        this.topic = topic;
//    }
//
//    public User getUser() { return user; }
//
//    public void setUser(User user) { this.user = user; }
//
//    public Topic getTopic() { return topic; }
//
//    public void setTopic(Topic topic) { this.topic = topic; }

    public UserTopic(String username, String userRole, String topicName, String topicCategory, Timestamp date) {
        this.userName = username;
        this.userRole = userRole;
        this.topicName = topicName;
        this.topicCategory = topicCategory;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public UserTopic setUserName(String username) {
        this.userName = username;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public UserTopic setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getTopicName() {
        return topicName;
    }

    public UserTopic setTopicName(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public UserTopic setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
        return this;
    }

    public Timestamp getDate() {
        return date;
    }

    public UserTopic setDate(Timestamp date) {
        this.date = date;
        return this;
    }
}
