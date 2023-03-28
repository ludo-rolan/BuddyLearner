package com.example.buddylearner.ui.base.transform;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.buddylearner.data.datasources.HomeDataSource;
import com.example.buddylearner.data.datasources.TransformDataSource;
import com.example.buddylearner.data.repositories.HomeRepository;
import com.example.buddylearner.data.repositories.TransformRepository;
import com.example.buddylearner.ui.base.HomeViewModel;


/**
 * ViewModel provider factory to instantiate HomeViewModel.
 * Required given HomeViewModel has a non-empty constructor
 */
public class TransformViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TransformViewModel.class)) {
            return (T) new TransformViewModel(TransformRepository.getInstance(new TransformDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
