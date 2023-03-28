package com.example.buddylearner.ui.base.transform;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.model.UserTopic;
import com.example.buddylearner.data.repositories.HomeRepository;
import com.example.buddylearner.data.repositories.TransformRepository;

import java.util.ArrayList;
import java.util.List;

public class TransformViewModel extends ViewModel {

    private final MutableLiveData<List<String>> mTexts = new MutableLiveData<>();
    private final MutableLiveData<List<UserTopic>> tutorUsersUserTopics = new MutableLiveData<>();
    private final MutableLiveData<List<UserTopic>> userFollowedTopics = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
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

        List<String> texts = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            texts.add("This is item # " + i);
        }
        mTexts.setValue(texts);
    }

    public LiveData<List<String>> getTexts() {
        return mTexts;
    }

    public void loadTutorUsers(User user, List<UserTopic> userFollowedTopics) {
//        List<UserTopic> userFollowedTopics = null;
//
//        loadUserFollowedTopics(username);
//        if(getUserFollowedTopics() != null) userFollowedTopics = getUserFollowedTopics().getValue();

//        if(userFollowedTopics != null)
//            if(userFollowedTopics.getValue() != null)
//                Log.d(TAG, "userFollowedTopics is not empty !" + userFollowedTopics.getValue().get(0).getTopic().getName());
//        else Log.d(TAG, "userFollowedTopics is completely empty !");
//
//        if(userFollowedTopics != null)
//            transformRepository.loadTutorUsers(username, userFollowedTopics.getValue(), tutorUsersUserTopics::setValue, Throwable::printStackTrace);
//        else transformRepository.loadTutorUsers(username, null, tutorUsersUserTopics::setValue, Throwable::printStackTrace);

        transformRepository.loadTutorUsers(user, userFollowedTopics, tutorUsersUserTopics::setValue, Throwable::printStackTrace);

    }

    public LiveData<List<UserTopic>> getTutorUsers() { return tutorUsersUserTopics; }

    public void loadUserFollowedTopics (User user) {
        transformRepository.loadUserFollowedTopics(user, userFollowedTopics::setValue, Throwable::printStackTrace);
    }

    public LiveData<List<UserTopic>> getUserFollowedTopics() { return userFollowedTopics; }

    public void loadCurrentUser(String currentUsername) {
        transformRepository.loadCurrentUser(currentUsername, currentUser::setValue, Throwable::printStackTrace);
    }

    public LiveData<User> getCurrentUser() { return currentUser; }

}