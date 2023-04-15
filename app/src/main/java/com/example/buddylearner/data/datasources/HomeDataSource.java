package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.buddylearner.data.enums.UserRole;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.HomeResult;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeDataSource {

    FirebaseDataSource dataSource = new FirebaseDataSource();

    public void logout() {
        // TODO: revoke authentication
        dataSource.getFirebaseAuthInstance().signOut();
    }

    public boolean isFirstConnection () {
        FirebaseUserMetadata metadata = Objects.requireNonNull(dataSource.getFirebaseAuthInstance().getCurrentUser()).getMetadata();

        return Objects.requireNonNull(metadata).getCreationTimestamp() == metadata.getLastSignInTimestamp();
    }


    public void allUser(OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {
        dataSource.getFirebaseFirestoreInstance()
                .collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        User user = documentSnapshot.toObject(User.class);
                        users.add(user);
                    }
                    successListener.onSuccess(users);
                })
                .addOnFailureListener(failureListener);
    }


    public void findUser(OnSuccessListener<User> successListener, OnFailureListener failureListener, String username) {

        if(username != null) {
            dataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = new User(
                                documentSnapshot.getString("userId"),
                                documentSnapshot.getString("userName"),
                                documentSnapshot.getString("email"),
//                            documentSnapshot.getString("password"),
                                documentSnapshot.getString("role").equalsIgnoreCase(UserRole.learner.name()) ? UserRole.learner : UserRole.tutor
                        );

                        Log.d(TAG, "HomeDataSource current username: " + user.getUserName());

                        successListener.onSuccess(user);
                    })
                    .addOnFailureListener(failureListener);
        }
        else {
            Log.w(TAG, "username is null");
        }
    }

}
