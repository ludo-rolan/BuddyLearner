package com.example.buddylearner.ui.base;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.R;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.HomeRepository;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<HomeUiState> homeUiState = new MutableLiveData<>();
    private MutableLiveData<HomeResult> homeResult = new MutableLiveData<>();
    private HomeRepository homeRepository;

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    LiveData<HomeUiState> getHomeUiState() {
        return homeUiState;
    }

    LiveData<HomeResult> getHomeResult() {
        return homeResult;
    }

    public boolean isUserFirstConnection () {

        return homeRepository.getUserConnectionOccurrence();

    }

    // méthode qui permet de récupérer les utilisateurs
    public LiveData<List<User>> getUsers() {
        //Log.d(TAG, "users data type : " + users.getValue().getClass());
        return users;
    }

    // méthode qui permet de recharger les utilisateurs
    public void loadUsers() {
        homeRepository.getUsers(users::postValue, Throwable::printStackTrace);
    }

    public LiveData<User> getUser() { return user; }

    public void loadUser(String username) {
        homeRepository.getUser(user::setValue, Throwable::printStackTrace, username);
        Log.d(TAG, "HomeViewModel current user: " + user.getValue());
    }

    public void disconnectUser() {
        homeRepository.logout();
    }

}
