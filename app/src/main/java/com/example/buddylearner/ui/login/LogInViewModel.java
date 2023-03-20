package com.example.buddylearner.ui.login;

import static android.content.ContentValues.TAG;

import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class LogInViewModel extends ViewModel {

    private MutableLiveData<LogInUiState> logInUiState = new MutableLiveData<>();
    private MutableLiveData<LogInResult> logInResult = new MutableLiveData<>();
    private LogInRepository logInRepository;

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFirstConnection = new MutableLiveData<>();

    // strict regex
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    private static final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@\"\n" +
            "            + \"[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    // digit + lowercase char + uppercase char + punctuation + symbol
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    LogInViewModel(LogInRepository logInRepository) {
        this.logInRepository = logInRepository;
    }

    LiveData<LogInUiState> getLogInUiState() {
        return logInUiState;
    }

    LiveData<LogInResult> getLogInResult() {
        return logInResult;
    }

    public boolean logIn(String username, String password) {
        // can be launched in a separate asynchronous job
        LoggingInResult<User> result = logInRepository.logIn(
                username,
                password,
                isFirstConnection::setValue,
                Throwable::printStackTrace
        );

        if (result instanceof LoggingInResult.Success) {
            User data = ((LoggingInResult.Success<User>) result).getData();
            logInResult.setValue(new LogInResult(new LoggedInUserView(data.getUserName(), data.getPassword())));
            return true;
        } else {
            logInResult.setValue(new LogInResult(R.string.login_failed));
            return false;
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

    public boolean isUserFirstConnection () {

        return logInRepository.getUserConnectionOccurrence();

    }

    // méthode qui permet de récupérer les utilisateurs
    public LiveData<List<User>> getUsers() {
        return users;
    }

    // méthode qui permet de recharger les utilisateurs
    public void loadUsers() {
        logInRepository.getUsers(users::setValue, Throwable::printStackTrace);
    }

    public LiveData<User> getUser() { return user; }

    public void loadUser(String username) {
        logInRepository.getUser(user::setValue, Throwable::printStackTrace, username);
    }

    public LiveData<FirebaseUser> getFirebaseUser() { return firebaseUser; }

    public LiveData<Boolean> getIsFirstConnection () {
        return isFirstConnection;
    }

}
