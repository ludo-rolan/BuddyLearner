package com.example.buddylearner.ui.stateholders;

public class FunctionalityUiStateExample {

//    UI state for App Dashboard
//    needs a view data model
    private boolean isSignedIn = false;

    public FunctionalityUiStateExample(boolean isSignedIn) {
        this.isSignedIn = isSignedIn;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public FunctionalityUiStateExample setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
        return this;
    }

}
