package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.buddylearner.data.enums.UserRole;
import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.model.UserTopic;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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


    public void isFollowingTopic(OnSuccessListener<Boolean> successListener, OnFailureListener failureListener, String username, String topicName) {
        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(username)
//                .collection("isFollowing")
//                .document(topicName)
//                .get()
//                .addOnSuccessListener(DocumentSnapshot -> {
//                    if(DocumentSnapshot.exists()) {
//                        successListener.onSuccess(true);
//                    }
//                    else {
//                        // caused last mistake on document not found
//                        successListener.onSuccess(false);
//                    }
//                })
                .collection("isFollowing")
                .whereEqualTo("topicName", topicName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()) {
                            Log.d(TAG, "topic not followed !");
                            successListener.onSuccess(false);
                        }
                        else {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            successListener.onSuccess(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureListener.onFailure(e);
                    }
                });
    }


    public void startFollowingTopic(User user, String topicName, String topicCategory) {

        UserTopic userTopic = new UserTopic(user.getUserName(), user.getRole(), topicName, topicCategory, new Timestamp(new Date()));
        // UserTopic userTopic = new UserTopic(user, new Topic(topicName, topicCategory, new Timestamp(new Date())));

        // add topic to user topics list
//        dataSource.getFirebaseFirestoreInstance()
//                .collection("userTopics")
//                .document(user.getUserName())
//                .collection("topics")
//                .document(topicName)
//                .set(userTopic)
//                .add(userTopic)
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        Log.d(TAG, "user topic add successful !");
//                    }
//                })
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "user topic add successful !");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "user topic add failed !");
//                    }
//                });

        // add topic to user isFollowing list
        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(user.getUserName())
//                .collection("isFollowing")
//                .document(topicName)
//                .set(userTopic)
                .collection("isFollowing")
                .add(userTopic)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "start following topic successful !");
//                    }
//                })
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
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
//                .set(userTopic)
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


    public void stopFollowingTopic(User user, String topicName, String topicCategory) {

        UserTopic userTopic =  new UserTopic(user.getUserName(), user.getRole(), topicName, topicCategory, new Timestamp(new Date()));
        // UserTopic userTopic = new UserTopic(user, new Topic(topicName, topicCategory, new Timestamp(new Date())));

        // delete topic from user topics list
//        dataSource.getFirebaseFirestoreInstance()
//                .collection("userTopics")
//                .document(user.getUserName())
//                .collection("topics")
//                .document(topicName)
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "user topic deletion successful !");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "user topic deletion failed !");
//                    }
//                });

        // delete topic from isFollowing topic list
        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(user.getUserName())
//                .collection("isFollowing")
//                .document(topicName)
//                .delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "topic isFollowing deletion successful !");
//                    }
//                })
                .collection("isFollowing")
                .whereEqualTo("userName", user.getUserName())
                .whereEqualTo("userRole", user.getRole())
                .whereEqualTo("topicName", topicName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if(querySnapshot.isEmpty()) {
                                Log.d(TAG, "Aucun document trouvé");
                            }
                            else {
                                for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                    Log.d(TAG, "document name : " + documentSnapshot.getId());
                                }

                                for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                                    if(
                                            documentSnapshot.getString("userName").equalsIgnoreCase(user.getUserName()) &&
                                                    documentSnapshot.getString("userRole").equalsIgnoreCase(user.getRole()) &&
                                                    documentSnapshot.getString("topicName").equalsIgnoreCase(topicName)
                                    ) {
                                        String documentId = documentSnapshot.getId();

                                        // delete document with name topicName
                                        documentSnapshot.getReference().delete();

                                        Log.d(TAG, "topic isFollowing deletion successful !");

                                        break;
                                    }
                                }
                            }

                        }
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
//                .set(userTopic)
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


    public void getUser(OnSuccessListener<User> successListener, OnFailureListener failureListener)
    {
        String username = dataSource.getFirebaseAuthInstance()
                .getCurrentUser()
                .getDisplayName();

        if(!username.isEmpty()) {
            dataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = new User(
                                documentSnapshot.getString("userId"),
                                documentSnapshot.getString("userName"),
                                documentSnapshot.getString("email"),
                                documentSnapshot.getString("role").equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner : UserRole.tutor
                        );

                        successListener.onSuccess(user);
                    })
                    .addOnFailureListener(failureListener);
        }

        else failureListener.onFailure(new Exception("user not found!"));
    }

}
