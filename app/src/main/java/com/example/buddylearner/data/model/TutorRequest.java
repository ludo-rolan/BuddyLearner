package com.example.buddylearner.data.model;

import com.google.firebase.Timestamp;

public class TutorRequest {

    String userName;
    String tutorName;
    String topicName;
    String topicCategory;
    Timestamp date;

    public TutorRequest(String userName, String tutorName, String topicName, String topicCategory, Timestamp date) {
        this.userName = userName;
        this.tutorName = tutorName;
        this.topicName = topicName;
        this.topicCategory = topicCategory;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
