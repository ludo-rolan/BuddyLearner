package com.example.buddylearner.ui.login;

import androidx.annotation.Nullable;

public class LogInResult {

    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    LogInResult(@Nullable Integer error) {
        this.error = error;
    }

    LogInResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

}
