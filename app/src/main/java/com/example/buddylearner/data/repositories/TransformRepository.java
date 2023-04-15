package com.example.buddylearner.data.repositories;

import com.example.buddylearner.data.datasources.TopicsPageDataSource;
import com.example.buddylearner.data.datasources.TransformDataSource;
import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.model.UserTopic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class TransformRepository {

    private static volatile TransformRepository instance;

    private TransformDataSource dataSource;

    // private constructor : singleton access
    private TransformRepository(TransformDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TransformRepository getInstance(TransformDataSource dataSource) {
        if (instance == null) {
            instance = new TransformRepository(dataSource);
        }
        return instance;
    }

    public void getTopicsCategories(OnSuccessListener<List<TopicsCategory>> successListener, OnFailureListener failureListener) {
        dataSource.allTopicsCategories(successListener, failureListener);
    }

    public void getTopics(OnSuccessListener<List<Topic>> successListener, OnFailureListener failureListener) {
        dataSource.allTopics(successListener, failureListener);
    }

    public void getIsFollowingTopic(OnSuccessListener<Boolean> successListener, OnFailureListener failureListener, String username, String topicName) {
        dataSource.isFollowingTopic(successListener, failureListener, username, topicName);
    }

    public void startFollowingTopic(String username, String topicName, String topicCategory) {
        dataSource.startFollowingTopic(username, topicName, topicCategory);
    }

    public void stopFollowingTopic(String username, String topicName, String topicCategory) {
        dataSource.stopFollowingTopic(username, topicName, topicCategory);
    }

    public void getUsername(OnSuccessListener<String> successListener, OnFailureListener failureListener) {
        dataSource.getUsername(successListener, failureListener);
    }

    public void loadTutorUsers(User user, List<UserTopic> userFollowedTopics, OnSuccessListener<List<UserTopic>> successListener, OnFailureListener failureListener) {
        dataSource.loadTutorUsers(user, userFollowedTopics, successListener, failureListener);
    }

    public void loadUserFollowedTopics(User user, OnSuccessListener<List<UserTopic>> successListener, OnFailureListener failureListener) {
        dataSource.loadUserFollowedTopics(user, successListener, failureListener);
    }

    public void loadCurrentUser(OnSuccessListener<User> successListener, OnFailureListener failureListener) {
        dataSource.loadCurrentUser(successListener, failureListener);
    }

    public void sendTutorRequest(String tutorName) {
        dataSource.sendTutorRequest(tutorName);
    }

    public void loadTutor(String tutorName, OnSuccessListener<User> successListener, OnFailureListener failureListener) {
        dataSource.loadTutor(tutorName, successListener, failureListener);
    }

    public void loadTutorTopics(String tutorName, OnSuccessListener<List<UserTopic>> successListener, OnFailureListener failureListener) {
        dataSource.loadTutorTopics(tutorName, successListener, failureListener);
    }

}
