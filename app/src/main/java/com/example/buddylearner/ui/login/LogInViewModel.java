package com.example.buddylearner.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LogInRepository;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.SigningUpResult;
import com.example.buddylearner.ui.signup.SignUpResult;
import com.example.buddylearner.ui.signup.SignUpUiState;
import com.example.buddylearner.ui.signup.SignedUpUserView;

public class LogInViewModel extends ViewModel {

    private MutableLiveData<LogInUiState> logInUiState = new MutableLiveData<>();
    private MutableLiveData<LogInResult> logInResult = new MutableLiveData<>();
    private LogInRepository logInRepository;

    LogInViewModel(LogInRepository logInRepository) {
        this.logInRepository = logInRepository;
    }

    LiveData<LogInUiState> getLogInUiState() {
        return logInUiState;
    }

    LiveData<LogInResult> getLogInResult() {
        return logInResult;
    }

    public void logIn(String username, String password) {
        // can be launched in a separate asynchronous job
        LoggingInResult<User> result = logInRepository.logIn(username, password);

        if (result instanceof LoggingInResult.Success) {
            User data = ((LoggingInResult.Success<User>) result).getData();
            logInResult.setValue(new LogInResult(new LoggedInUserView(data.getUserName(), data.getPassword())));
        } else {
            logInResult.setValue(new LogInResult(R.string.login_failed));
        }
    }

    public void logInDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            logInUiState.setValue(new LogInUiState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            logInUiState.setValue(new LogInUiState(null, R.string.invalid_password));
        } else {
            logInUiState.setValue(new LogInUiState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("*") || username.contains("/") || username.contains("\\") || username.contains("[")) {
            return Patterns.GOOD_IRI_CHAR.matches("\n" +
                    "  ^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder email validation check
    private boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}
