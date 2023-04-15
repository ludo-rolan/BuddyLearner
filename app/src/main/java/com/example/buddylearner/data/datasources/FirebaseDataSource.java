package com.example.buddylearner.data.datasources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDataSource {

    private FirebaseAuth mAuth = null;
    private FirebaseFirestore mFirebaseFirestore = null;

//    singleton design pattern implemented for getting firebase authentication
    public synchronized   FirebaseAuth getFirebaseAuthInstance () {
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            return mAuth;
        }
        return mAuth;
    }

//    singleton design pattern implemented for getting firebase firestore
    public synchronized FirebaseFirestore getFirebaseFirestoreInstance () {
        if(mFirebaseFirestore == null) {
            mFirebaseFirestore = FirebaseFirestore.getInstance();
            return mFirebaseFirestore;
        }
        return mFirebaseFirestore;
    }

}
