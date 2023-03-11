package com.example.buddylearner.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.SigningUpResult;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<SignUpUiState> signUpUiState = new MutableLiveData<>();
    private MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();
    private SignUpRepository signUpRepository;

    SignUpViewModel(SignUpRepository loginRepository) {
        this.signUpRepository = loginRepository;
    }

    LiveData<SignUpUiState> getSignUpUiState() {
        return signUpUiState;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void signUp(String username, String email, String password) {
        // can be launched in a separate asynchronous job
        SigningUpResult<User> result = signUpRepository.signUp(username, email, password);

        if (result instanceof SigningUpResult.Success) {
            User data = ((SigningUpResult.Success<User>) result).getData();
            signUpResult.setValue(new SignUpResult(new SignedUpUserView(data.getUserName(), data.getEmail(), data.getPassword())));
        } else {
            signUpResult.setValue(new SignUpResult(R.string.login_failed));
        }
    }

    public void signUpDataChanged(String username, String email, String password) {
        if (!isUserNameValid(username)) {
            signUpUiState.setValue(new SignUpUiState(R.string.invalid_username, null, null));
        } else if (!isEmailValid(email)) {
            signUpUiState.setValue(new SignUpUiState(null, R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            signUpUiState.setValue(new SignUpUiState(null, null, R.string.invalid_password));
        } else {
            signUpUiState.setValue(new SignUpUiState(true));
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
