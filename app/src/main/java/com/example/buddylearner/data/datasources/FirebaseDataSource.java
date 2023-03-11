package com.example.buddylearner.data.datasources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDataSource {

    public static FirebaseAuth mAuth = null;
    public static FirebaseFirestore mFirebaseFirestore = null;

//    singleton design pattern implemented for getting firebase authentication
    public static FirebaseAuth getFirebaseAuthInstance () {
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            return mAuth;
        }
        return mAuth;
    }

//    singleton design pattern implemented for getting firebase firestore
    public static FirebaseFirestore getFirebaseFirestoreInstance () {
        if(mFirebaseFirestore == null) {
            mFirebaseFirestore = FirebaseFirestore.getInstance();
            return mFirebaseFirestore;
        }
        return mFirebaseFirestore;
    }

}
