package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransformDataSource {


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


    public void isFollowingTopic(OnSuccessListener<Boolean> successListener, OnFailureListener failureListener, String username, String topicName) {
        dataSource.getFirebaseFirestoreInstance()
                .collection("following")
                .document(username)
                .collection("isFollowing")
                .document(topicName)
                .get()
                .addOnSuccessListener(DocumentSnapshot -> {
                    if(DocumentSnapshot.exists()) {
                        successListener.onSuccess(true);
                    }
                    else {
                        // caused last mistake on document not found
                        successListener.onSuccess(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureListener.onFailure(e);
                    }
                });
    }


    public void startFollowingTopic(String username, String topicName, String topicCategory) {

        Topic topic = new Topic(topicName, topicCategory, new Timestamp(new Date()));

        dataSource.getFirebaseFirestoreInstance()
                .collection("following")
                .document(username)
                .collection("isFollowing")
                .document(topicName)
                .set(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "start following topic successful !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "start following topic failed !");
                    }
                });

//        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(username)
//                .collection("startedFollowing")
//                .document(topicName)
//                .set(topic)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "start following topic successful !");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "start following topic failed !");
//                    }
//                });
    }


    public void stopFollowingTopic(String username, String topicName, String topicCategory) {

        Topic topic = new Topic(topicName, topicCategory, new Timestamp(new Date()));

        dataSource.getFirebaseFirestoreInstance()
                .collection("following")
                .document(username)
                .collection("isFollowing")
                .document(topicName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "topic isFollowing deletion successful !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "topic isFollowing deletion failed !");
                    }
                });

//        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(username)
//                .collection("stoppedFollowing")
//                .document(topicName)
//                .set(topic)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "stopped following topic add successful !");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "stopped following topic add failed !");
//                    }
//                });

    }


    public void getUsername(OnSuccessListener<String> successListener, OnFailureListener failureListener)
    {
        String username = dataSource.getFirebaseAuthInstance()
                .getCurrentUser()
                .getDisplayName();

        if(!username.isEmpty())
            successListener.onSuccess(username);
        else failureListener.onFailure(new Exception("username not found!"));
    }

    public void loadUserFollowedTopics (String username, OnSuccessListener<List<Topic>> successListener, OnFailureListener failureListener) {

        dataSource.getFirebaseFirestoreInstance()
                .collection("following")
                .document(username)
                .collection("isFollowing")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Topic> userFollowedTopics = new ArrayList<>();

                        if(!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                if(documentSnapshot.exists()) {
                                    Topic followedTopic = documentSnapshot.toObject(Topic.class);
                                    userFollowedTopics.add(followedTopic);
                                }
                            }
                            successListener.onSuccess(userFollowedTopics);
                        }
                        else {
                            successListener.onSuccess(null);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "loading current user followed topic failed !");
                    }
                });

    }

    public void loadTutorUsers(String username, List<Topic> userFollowedTopics, OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {

        //TODO: Handle tutor users following the same topic as the current user load
        dataSource.getFirebaseFirestoreInstance()
                .collection("following")
                .document(username)
                .collection("isFollowing")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Topic> userFollowedTopics = new ArrayList<>();

                        if(!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                if(documentSnapshot.exists()) {
                                    Topic followedTopic = documentSnapshot.toObject(Topic.class);
                                    userFollowedTopics.add(followedTopic);
                                }
                            }
                            //successListener.onSuccess(userFollowedTopics);
                        }
                        else {
                            successListener.onSuccess(null);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "loading current user followed topic failed !");
                    }
                });
    }

}
