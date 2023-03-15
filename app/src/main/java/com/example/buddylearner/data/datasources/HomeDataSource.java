package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

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

    public HomeResult<User> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            User user = new User(username, password);

            // TODO: handle user authentication --

            FirebaseDataSource.getFirebaseAuthInstance()
                    .signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser currentUser = FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser();

//                                updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                        }
                    });

            // TODO: -- handle user authentication

            return new HomeResult.Success<>(user);
        } catch (Exception e) {
            return new HomeResult.Error(new IOException("Error in home activity", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseDataSource.getFirebaseAuthInstance().signOut();
    }

    public boolean isFirstConnection () {
        FirebaseUserMetadata metadata = Objects.requireNonNull(FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser()).getMetadata();

        return Objects.requireNonNull(metadata).getCreationTimestamp() == metadata.getLastSignInTimestamp();
    }


    public void allUser(OnSuccessListener<List<User>> successListener, OnFailureListener failureListener) {
        FirebaseDataSource.getFirebaseFirestoreInstance()
                .collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        User user = new User(documentSnapshot.getString("username"), documentSnapshot.getString("password"));
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

                    Log.d(TAG, "HomeDataSource current username: " + user.getUserName());

                    successListener.onSuccess(user);
                })
                .addOnFailureListener(failureListener);
    }

}
