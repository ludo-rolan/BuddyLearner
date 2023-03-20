package com.example.buddylearner.data.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

public class Topic {

    private String name;
    private String topicCategory;
    @Nullable
    private Timestamp date;

    public Topic () {}

    // constructor for log in
    public Topic(String name, String topicCategory) {
        this.name = name;
        this.topicCategory = topicCategory;
    }

    public Topic(String name, String topicCategory, Timestamp date) {
        this.name = name;
        this.topicCategory = topicCategory;
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    @Nullable
    public Timestamp getDate() { return date; }

    public void setDate (Timestamp date) { this.date = date; }
}
