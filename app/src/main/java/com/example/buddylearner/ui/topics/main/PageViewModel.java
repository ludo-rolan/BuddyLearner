package com.example.buddylearner.ui.topics.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.repositories.SignUpRepository;
import com.example.buddylearner.data.repositories.TopicsPageRepository;

import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<List<TopicsCategory>> topicsCategories = new MutableLiveData<>();
    private MutableLiveData<List<Topic>> topics = new MutableLiveData<>();
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

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public void loadTopicsCategory() {
        topicsPageRepository.getTopicsCategories(topicsCategories::setValue, Throwable::printStackTrace);
    }

    public void loadTopics() {
        topicsPageRepository.getTopics(topics::setValue, Throwable::printStackTrace);
    }
}