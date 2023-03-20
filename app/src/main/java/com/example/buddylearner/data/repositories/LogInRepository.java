package com.example.buddylearner.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.buddylearner.data.datasources.FirebaseDataSource;
import com.example.buddylearner.data.datasources.LogInDataSource;
import com.example.buddylearner.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class LogInRepository {

    private static volatile LogInRepository instance;

    private LogInDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private LogInRepository(LogInDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LogInRepository getInstance(LogInDataSource dataSource) {
        if (instance == null) {
            instance = new LogInRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        instance.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public synchronized LoggingInResult<User> logIn(
            String username,
            String password,
            OnSuccessListener<Boolean> successListener,
            OnFailureListener failureListener
    ) {
        // handle login
        LoggingInResult<User> result = dataSource.login(
                username,
                password,
                successListener,
                failureListener
        );
        if (result instanceof LoggingInResult.Success) {
            setLoggedInUser(((LoggingInResult.Success<User>) result).getData());
        }
        return result;
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
