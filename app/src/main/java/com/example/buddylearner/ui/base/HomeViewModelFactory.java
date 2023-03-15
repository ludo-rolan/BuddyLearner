package com.example.buddylearner.ui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.data.datasources.HomeDataSource;
import com.example.buddylearner.data.repositories.HomeRepository;


/**
 * ViewModel provider factory to instantiate LogInViewModel.
 * Required given LogInViewModel has a non-empty constructor
 */
public class HomeViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(HomeRepository.getInstance(new HomeDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
