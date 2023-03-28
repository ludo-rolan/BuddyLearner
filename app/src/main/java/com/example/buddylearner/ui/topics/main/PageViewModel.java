package com.example.buddylearner.ui.topics.main;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.TopicsPageRepository;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final MutableLiveData<List<TopicsCategory>> topicsCategories = new MutableLiveData<>();
    private final MutableLiveData<List<Topic>> topics = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFollowingTopic = new MutableLiveData<>();
    // user with username and role
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private TopicsPageRepository topicsPageRepository;

    public PageViewModel(TopicsPageRepository topicsPageRepository){ this.topicsPageRepository = topicsPageRepository; }

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<TopicsCategory>> getTopicsCategories () { return topicsCategories; }

    public LiveData<List<Topic>> getTopics () { return topics; }

    public LiveData<User> getUser () { return user; }

    public LiveData<Boolean> getIsFollowingTopic () { return isFollowingTopic; }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void loadTopicsCategory() {
        topicsPageRepository.getTopicsCategories(topicsCategories::postValue, Throwable::printStackTrace);
    }

    public void loadTopics() {
        topicsPageRepository.getTopics(topics::postValue, Throwable::printStackTrace);
    }

    public void loadIsFollowingTopic(String username, String topicName) {
        topicsPageRepository.getIsFollowingTopic(isFollowingTopic::postValue, Throwable::printStackTrace, username, topicName);
    }

    public void loadUser() {
        topicsPageRepository.getUser(user::postValue, Throwable::printStackTrace);
    }

    public void startFollowingTopic(User user, String topicName, String topicCategory) {
        topicsPageRepository.startFollowingTopic(user, topicName, topicCategory);
    }

    public void stopFollowingTopic(User user, String topicName, String topicCategory) {
        topicsPageRepository.stopFollowingTopic(user, topicName, topicCategory);
    }

}