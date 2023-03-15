package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogInDataSource {

//    public LoggingInResult<User> login(
//            OnSuccessListener<FirebaseUser> authSuccessListener,
//            OnFailureListener authFailureListener,
//            OnSuccessListener<Boolean> firstConnectionSuccessListener,
//            OnFailureListener firstConnectionFailureListener,
//            String username,
//            String password
//    ) {
//
//        try {
//
//            FirebaseDataSource.getFirebaseFirestoreInstance()
//                    .collection("users")
//                    .document(username)
//                    .get()
//                    .addOnCompleteListener(task -> {
//
//                        if(task.isSuccessful()) {
//
//                            FirebaseDataSource.getFirebaseAuthInstance()
//                                    .signInWithEmailAndPassword(task.getResult().getString("email"), password)
//                                    .addOnSuccessListener(authTask -> {
//                                        if (authTask.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//
//                                            // check if first connection
//                                            authTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                                    Log.d(TAG, "signInWithEmail:success");
//                                                    FirebaseUser currentUser = task.getResult().getUser();
//                                                    authSuccessListener.onSuccess(currentUser);
//
//                                                    if (task.isSuccessful()) {
//
//                                                        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                                Boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
//                                                                Log.d("MyTAG", "onComplete: " + (isNew ? "new user" : "old user"));
//
//
//                                                                Log.d("MyTAG", "firstconnection: " + isNew);
//                                                                firstConnectionSuccessListener.onSuccess(isNew);
//                                                            }
//                                                        });
//
//
//                                                    } else {
//                                                        firstConnectionFailureListener.onFailure(task.getException());
//                                                    }
//
//                                                }
//                                            });
//
//
////                                updateUI(currentUser);
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "signInWithEmail:failure", authTask.getException());
//                                            authFailureListener.onFailure(authTask.getException());
//                                        }
//                                    });
//
//                        } else {
//                            Log.w(TAG, "user not found:failure", task.getException());
//                        }
//
//                    });
//
//            // TODO: -- handle user authentication
//            User user = new User(username, password);
//            return new LoggingInResult.Success<>(user);
//        } catch (Exception e) {
//            return new LoggingInResult.Error(new IOException("Error logging in", e));
//        }
//    }




    public LoggingInResult<User> login(
            MutableLiveData<Boolean> firstConnectionSuccessListener,
            OnFailureListener firstConnectionFailureListener,
            String username,
            String password
    ) {

        try {

            FirebaseDataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnCompleteListener(task -> {

                            if(task.isSuccessful()) {

                                DocumentSnapshot document = task.getResult();

                                if(document.exists()) {

                                    Log.d(TAG, "Document exists ! user email: " + task.getResult().getData().get("email").toString());

                                    FirebaseDataSource.getFirebaseAuthInstance()
                                            .signInWithEmailAndPassword(task.getResult().getData().get("email").toString(), password)
                                            .addOnSuccessListener(authResult -> {

                                                Log.d(TAG, "signInWithEmail:success");

                                                Boolean isNew = authResult.getAdditionalUserInfo().isNewUser();
                                                Log.d("MyTAG", "onComplete: " + (isNew ? "new user" : "old user"));

                                                Log.d("MyTAG", "firstconnection: " + isNew);
                                                MutableLiveData<Boolean> isUserNew = new MutableLiveData<>(isNew);
                                                firstConnectionSuccessListener.setValue(isNew);

                                                Log.d(TAG, "is new user last try : " + firstConnectionSuccessListener.getValue());
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    firstConnectionFailureListener.onFailure(e);
                                                }
                                            });

                                } else {

                                    Log.d(TAG, "Document doesn't exist !");

                                }


                            } else {
                                Log.d(TAG, "get firebase firestore user failed: " + task.getException());
                            }

                            });

            // TODO: -- handle user authentication
            User user = new User(username, password);
            return new LoggingInResult.Success<>(user);
        } catch (Exception e) {
            return new LoggingInResult.Error(new IOException("Error logging in", e));
        }
    }




    public void logout() {
        // TODO: revoke authentication
        FirebaseDataSource.getFirebaseAuthInstance().signOut();
    }




    public boolean isFirstConnection () {

        if(FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser() != null) {
            FirebaseUserMetadata metadata =  FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser().getMetadata();

            Log.d(TAG, "metadata timestamp comparison: " + metadata.getCreationTimestamp() + metadata.getLastSignInTimestamp());

            return metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp();
        }

        return false;
    }


    public void allUser(OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {
        FirebaseDataSource.getFirebaseFirestoreInstance()
                .collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        User user = new User(documentSnapshot.getString("userName"), documentSnapshot.getString("password"));
                        users.add(user);
                    }
                    successListener.onSuccess(users);
                })
                .addOnFailureListener(failureListener);
    }


    public void findUser(OnSuccessListener<User> successListener, OnFailureListener failureListener, String username) {
        FirebaseDataSource.getFirebaseFirestoreInstance()
                .collection("users")
                .document(username)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = new User(documentSnapshot.getString("userName"), documentSnapshot.getString("email"), documentSnapshot.getString("password"));

                    successListener.onSuccess(user);
                })
                .addOnFailureListener(failureListener);
    }

}
