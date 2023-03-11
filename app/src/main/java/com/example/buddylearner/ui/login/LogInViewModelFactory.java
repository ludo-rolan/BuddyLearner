package com.example.buddylearner.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.data.datasources.LogInDataSource;
import com.example.buddylearner.data.repositories.LogInRepository;


/**
 * ViewModel provider factory to instantiate LogInViewModel.
 * Required given LogInViewModel has a non-empty constructor
 */
public class LogInViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LogInViewModel.class)) {
            return (T) new LogInViewModel(LogInRepository.getInstance(new LogInDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
