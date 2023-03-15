package com.example.buddylearner.ui.topics.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.data.datasources.SignUpDataSource;
import com.example.buddylearner.data.datasources.TopicsPageDataSource;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.TopicsPageRepository;
import com.example.buddylearner.ui.signup.SignUpViewModel;

/**
 * ViewModel provider factory to instantiate SignUpViewModel.
 * Required given SignUpViewModel has a non-empty constructor
 */
public class TopicsPageViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(PageViewModel.class)) {
            return (T) new PageViewModel(TopicsPageRepository.getInstance(new TopicsPageDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}