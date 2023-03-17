package com.example.buddylearner.data.repositories;

import com.example.buddylearner.data.datasources.HomeDataSource;
import com.example.buddylearner.data.datasources.LogInDataSource;
import com.example.buddylearner.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class HomeRepository {

    private static volatile HomeRepository instance;

    private HomeDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private HomeRepository(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HomeRepository getInstance(HomeDataSource dataSource) {
        if (instance == null) {
            instance = new HomeRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setHomeUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public boolean getUserConnectionOccurrence () {

        return dataSource.isFirstConnection();

    }

    public void getUsers(OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {
        dataSource.allUser(successListener, failureListener);
    }

    public void getUser(OnSuccessListener<User> successListener, OnFailureListener failureListener, String username) {
        dataSource.findUser(successListener, failureListener, username);
    }

}
