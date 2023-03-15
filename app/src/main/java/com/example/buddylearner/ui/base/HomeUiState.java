package com.example.buddylearner.ui.base;

import androidx.annotation.Nullable;

public class HomeUiState {

    @Nullable
    private Integer currentUserName;
    private boolean isDataValid;


    // TODO: expérience de modification de mise à jour des champs de l'ui login

    // TODO: expérience de modification de mise à jour des champs de l'ui login


    HomeUiState(@Nullable Integer currentUserName) {
        this.currentUserName = currentUserName;
        this.isDataValid = false;
    }

    HomeUiState(boolean isDataValid) {
        this.currentUserName = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return currentUserName;
    }

    boolean isDataValid() {
        return isDataValid;
    }

}
