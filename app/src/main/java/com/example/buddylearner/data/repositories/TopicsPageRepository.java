package com.example.buddylearner.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.buddylearner.data.datasources.LogInDataSource;
import com.example.buddylearner.data.datasources.TopicsPageDataSource;
import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.function.Consumer;

public class TopicsPageRepository {

    private static volatile TopicsPageRepository instance;

    private TopicsPageDataSource dataSource;

    // private constructor : singleton access
    private TopicsPageRepository(TopicsPageDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TopicsPageRepository getInstance(TopicsPageDataSource dataSource) {
        if (instance == null) {
            instance = new TopicsPageRepository(dataSource);
        }
        return instance;
    }

    public void getTopicsCategories(OnSuccessListener<List<TopicsCategory>> successListener, OnFailureListener failureListener) {
        dataSource.allTopicsCategories(successListener, failureListener);
    }

    public void getTopics(OnSuccessListener<List<Topic>> successListener, OnFailureListener failureListener) {
        dataSource.allTopics(successListener, failureListener);
    }

}
