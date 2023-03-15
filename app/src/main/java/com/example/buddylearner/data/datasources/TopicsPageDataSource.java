package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopicsPageDataSource {


    public void allTopicsCategories(OnSuccessListener<List<TopicsCategory>> successListener, OnFailureListener failureListener) {
        FirebaseDataSource.getFirebaseFirestoreInstance()
                .collection("topicsCategory")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<TopicsCategory> topicsCategories = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        TopicsCategory topicsCategory = new TopicsCategory(documentSnapshot.getString("name"));
                        topicsCategories.add(topicsCategory);
                    }
                    successListener.onSuccess(topicsCategories);
                })
                .addOnFailureListener(failureListener);
    }


    public void allTopics(OnSuccessListener<List<Topic>> successListener, OnFailureListener failureListener) {
        FirebaseDataSource.getFirebaseFirestoreInstance()
                .collection("topics")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Topic> topics = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Topic topic = new Topic(documentSnapshot.getString("name"));
                        topics.add(topic);
                    }
                    successListener.onSuccess(topics);
                })
                .addOnFailureListener(failureListener);
    }


}
