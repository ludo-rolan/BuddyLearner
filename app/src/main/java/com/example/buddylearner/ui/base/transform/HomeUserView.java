package com.example.buddylearner.ui.base.transform;

public class HomeUserView {

    private String username;
    //... other data fields that may be accessible to the UI

    HomeUserView(String username) {
        this.username = username;
    }

    String getUserName() {
        return username;
    }

}
