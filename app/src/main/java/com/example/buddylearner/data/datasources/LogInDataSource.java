package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.repositories.LoggingInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class LogInDataSource {

    public LoggingInResult<User> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            User user = new User(username, password);

            // TODO: handle user authentication --

            FirebaseDataSource.getFirebaseAuthInstance()
                    .signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
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
                        }
                    });

            // TODO: -- handle user authentication

            return new LoggingInResult.Success<>(user);
        } catch (Exception e) {
            return new LoggingInResult.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseDataSource.getFirebaseAuthInstance().signOut();
    }

}
