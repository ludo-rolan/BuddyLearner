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
import java.util.function.Consumer;

public class TopicsPageDataSource {


    FirebaseDataSource dataSource = new FirebaseDataSource();


    public void allTopicsCategories(OnSuccessListener<List<TopicsCategory>> successListener, OnFailureListener failureListener) {
        dataSource.getFirebaseFirestoreInstance()
                .collection("topicsCategories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<TopicsCategory> topicsCategories = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        TopicsCategory topicsCategory = documentSnapshot.toObject(TopicsCategory.class);
                        topicsCategories.add(topicsCategory);
                    }
                    successListener.onSuccess(topicsCategories);
                })
                .addOnFailureListener(failureListener);
    }


    public void allTopics(OnSuccessListener<List<Topic>> successListener, OnFailureListener failureListener) {
        dataSource.getFirebaseFirestoreInstance()
                .collection("topics")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Topic> topics = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Topic topic = documentSnapshot.toObject(Topic.class);
                        topics.add(topic);
                    }
                    successListener.onSuccess(topics);
                })
                .addOnFailureListener(failureListener);
    }


}
