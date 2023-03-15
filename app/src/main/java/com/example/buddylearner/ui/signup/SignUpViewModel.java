package com.example.buddylearner.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.SigningUpResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<SignUpUiState> signUpUiState = new MutableLiveData<>();
    private MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();
    private SignUpRepository signUpRepository;

    // strict regex
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$";
    private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
            "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    SignUpViewModel(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
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
    private boolean isUserNameValid(final String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    // A placeholder email validation check
    private boolean isEmailValid(final String username) {
        Matcher matcher = emailPattern.matcher(username);
        return matcher.matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(final String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

}
