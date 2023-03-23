package com.example.buddylearner.data.repositories;

import com.example.buddylearner.data.datasources.LogInDataSource;
import com.example.buddylearner.data.datasources.SignUpDataSource;
import com.example.buddylearner.data.enums.UserRole;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.ui.signup.SignUpResult;

public class SignUpRepository {

    private static volatile SignUpRepository instance;

    private SignUpDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private SignUpRepository(SignUpDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static SignUpRepository getInstance(SignUpDataSource dataSource) {
        if (instance == null) {
            instance = new SignUpRepository(dataSource);
        }
        return instance;
    }

    public boolean isSignedUp() {
        return user != null;
    }

    public void deleteAccount() {
        user = null;
        dataSource.deleteAccount();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public SigningUpResult<User> signUp(String username, String email, String password, UserRole role) {
        // handle signing up
        SigningUpResult<User> result = dataSource.signUp(username, email, password, role);

        if (result instanceof SigningUpResult.Success) {
            setLoggedInUser(((SigningUpResult.Success<User>) result).getData());
        }
        return result;
    }

}
