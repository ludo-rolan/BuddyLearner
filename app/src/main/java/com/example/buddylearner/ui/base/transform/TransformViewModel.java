package com.example.buddylearner.ui.base.transform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.HomeRepository;
import com.example.buddylearner.data.repositories.TransformRepository;

import java.util.ArrayList;
import java.util.List;

public class TransformViewModel extends ViewModel {

    private final MutableLiveData<List<String>> mTexts = new MutableLiveData<>();
    private final MutableLiveData<List<User>> tutorUsers = new MutableLiveData<>();
    private final MutableLiveData<List<Topic>> userFollowedTopics = new MutableLiveData<>();
    private TransformRepository transformRepository;

    public TransformViewModel() {
        //mTexts = new MutableLiveData<>();
        List<String> texts = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            texts.add("This is item # " + i);
        }
        mTexts.setValue(texts);
    }

    public TransformViewModel(TransformRepository transformRepository) {
        this.transformRepository = transformRepository;
    }

    public LiveData<List<String>> getTexts() {
        return mTexts;
    }

    public void loadTutorUsers(String username) {
        List<Topic> userFollowedTopics = null;

        loadUserFollowedTopics(username);
        if(getUserFollowedTopics() != null) userFollowedTopics = getUserFollowedTopics().getValue();

        transformRepository.loadTutorUsers(username, userFollowedTopics, tutorUsers::postValue, Throwable::printStackTrace);
    }

    public LiveData<List<User>> getTutorUsers() { return tutorUsers; }

    public void loadUserFollowedTopics (String username) {
        transformRepository.loadUserFollowedTopics(username, userFollowedTopics::postValue, Throwable::printStackTrace);
    }

    public LiveData<List<Topic>> getUserFollowedTopics() { return userFollowedTopics; }

}