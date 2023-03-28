package com.example.buddylearner.ui.base.transform;

import androidx.annotation.Nullable;

public class HomeResult {

    @Nullable
    private HomeUserView success;
    @Nullable
    private Integer error;

    HomeResult(@Nullable Integer error) {
        this.error = error;
    }

    HomeResult(@Nullable HomeUserView success) {
        this.success = success;
    }

    @Nullable
    HomeUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

}
