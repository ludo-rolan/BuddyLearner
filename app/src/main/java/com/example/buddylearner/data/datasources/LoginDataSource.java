package com.example.buddylearner.data.datasources;

import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LoggingInResult;

import java.io.IOException;

public class LoginDataSource {

    public LoggingInResult<User> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            User fakeUser =
                    new User(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new LoggingInResult.Success<>(fakeUser);
        } catch (Exception e) {
            return new LoggingInResult.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}
