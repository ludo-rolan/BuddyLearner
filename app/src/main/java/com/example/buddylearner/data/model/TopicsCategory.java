package com.example.buddylearner.data.model;

import androidx.annotation.Nullable;

public class TopicsCategory {

    private String name;

    public TopicsCategory () {}

    // constructor for log in
    public TopicsCategory(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName (String name) {
        this.name = name;
    }

}
