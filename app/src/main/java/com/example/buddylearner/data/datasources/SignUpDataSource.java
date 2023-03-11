package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.example.buddylearner.data.repositories.SigningUpResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUpDataSource {

    public SigningUpResult<User> signUp(String username, String email, String password) {

        try {
            // TODO: handle User registration
            User signUpUser = new User(
                username,
                email,
                password
            );


            // Add a new document with declared id
            FirebaseDataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .set(signUpUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });


            // Add new user to FirebaseAuth
            FirebaseDataSource.getFirebaseAuthInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
//                                FirebaseUser currentUser = FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser();
//                                updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });





            // Add Uid to firebase firestore user document
            FirebaseDataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(username)
                    .update("userId", FirebaseDataSource.getFirebaseAuthInstance().getCurrentUser().getUid())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });


            return new SigningUpResult.Success<>(signUpUser);
        } catch (Exception e) {
            return new SigningUpResult.Error(new IOException("Error signing up", e));
        }
    }

    public void deleteAccount() {
        // TODO: delete account

    }

}
