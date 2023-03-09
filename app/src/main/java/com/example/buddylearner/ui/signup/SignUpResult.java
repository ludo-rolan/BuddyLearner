package com.example.buddylearner.ui.signup;

import androidx.annotation.Nullable;

public class SignUpResult {

    @Nullable
    private SignedUpUserView success;
    @Nullable
    private Integer error;

    SignUpResult(@Nullable Integer error) {
        this.error = error;
    }

    SignUpResult(@Nullable SignedUpUserView success) {
        this.success = success;
    }

    @Nullable
    SignedUpUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

}
