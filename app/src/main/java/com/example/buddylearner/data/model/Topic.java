package com.example.buddylearner.data.model;

import androidx.annotation.Nullable;

public class Topic {

    private String name;
    private String topicCategory;

    public Topic () {}

    // constructor for log in
    public Topic(String name, String topicCategory) {
        this.name = name;
        this.topicCategory = topicCategory;
    }

    public String getName() { return name; }
    public void setName() { this.name = name; }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }
}
