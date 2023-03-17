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
import java.util.function.Consumer;

public class LogInDataSource {

    FirebaseDataSource dataSource = new FirebaseDataSource();


    private boolean registrationSuccessful = false;

    public void register(String email, String password, String userName, Consumer<Boolean> onResult) {
        // Votre code pour enregistrer un utilisateur ici
        registrationSuccessful = true; // Supposons que l'enregistrement a réussi

        // Appeler la fonction de rappel avec le résultat de l'enregistrement
        onResult.accept(registrationSuccessful);
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }


    public LoggingInResult<User> newLogin(
            String email,
            String password,
            Boolean onResult
    ){
        return null;
    }


    public synchronized LoggingInResult<User> login(
            String username,
            String password,
            Consumer<Boolean> onResult
    ) {

        try {

            dataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnCompleteListener(task -> {

                            if(task.isSuccessful()) {

                                DocumentSnapshot document = task.getResult();

                                if(document.exists()) {

                                    Log.d(TAG, "Document exists ! user email: " + task.getResult().getData().get("email").toString());

                                    dataSource.getFirebaseAuthInstance()
                                            .signInWithEmailAndPassword(task.getResult().getData().get("email").toString(), password)
                                            .addOnSuccessListener(authResult -> {

                                                Log.d(TAG, "signInWithEmail:success");

                                                Boolean isNew = authResult.getAdditionalUserInfo().isNewUser();
                                                Log.d("MyTAG", "onComplete: " + (isNew ? "new user" : "old user"));

                                                onResult.accept(isNew);

                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Auth Failed: " + e.getStackTrace());
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
        dataSource.getFirebaseAuthInstance().signOut();
    }




    public synchronized boolean isFirstConnection () {

        if(dataSource.getFirebaseAuthInstance().getCurrentUser() != null) {
            FirebaseUserMetadata metadata =  dataSource.getFirebaseAuthInstance().getCurrentUser().getMetadata();

            Log.d(TAG, "metadata timestamp comparison: " + metadata.getCreationTimestamp() + metadata.getLastSignInTimestamp());

            return metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp();
        }

        return false;
    }


    public synchronized void allUser(OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {
        dataSource.getFirebaseFirestoreInstance()
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


    public synchronized void findUser(OnSuccessListener<User> successListener, OnFailureListener failureListener, String username) {
        dataSource.getFirebaseFirestoreInstance()
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
