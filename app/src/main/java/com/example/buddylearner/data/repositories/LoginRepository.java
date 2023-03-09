package com.example.buddylearner.data.repositories;

import com.example.buddylearner.data.datasources.LoginDataSource;
import com.example.buddylearner.data.model.User;

public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
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

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LoggingInResult<User> login(String username, String password) {
        // handle login
        LoggingInResult<User> result = dataSource.login(username, password);
        if (result instanceof LoggingInResult.Success) {
            setLoggedInUser(((LoggingInResult.Success<User>) result).getData());
        }
        return result;
    }

}
