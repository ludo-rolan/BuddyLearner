package com.example.buddylearner.ui.signup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.data.datasources.SignUpDataSource;
import com.example.buddylearner.data.repositories.SignUpRepository;

/**
 * ViewModel provider factory to instantiate SignUpViewModel.
 * Required given SignUpViewModel has a non-empty constructor
 */
public class SignUpViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(SignUpRepository.getInstance(new SignUpDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}