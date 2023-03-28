package com.example.buddylearner.data.datasources;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buddylearner.data.model.Topic;
import com.example.buddylearner.data.model.TopicsCategory;
import com.example.buddylearner.data.model.User;
import com.example.buddylearner.data.model.UserTopic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
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

        if(username != null)
            successListener.onSuccess(username);
        else failureListener.onFailure(new Exception("username not found!"));
    }

    public void loadUserFollowedTopics (User user, OnSuccessListener<List<UserTopic>> successListener, OnFailureListener failureListener) {

        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .document(username)
//                .collection("isFollowing")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<UserTopic> userFollowedTopics = new ArrayList<>();
//
//                        if(queryDocumentSnapshots != null) {
//                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                if(documentSnapshot.exists()) {
//                                    UserTopic followedTopic = documentSnapshot.toObject(UserTopic.class);
//                                    userFollowedTopics.add(followedTopic);
//                                }
//                            }
//
//                            for(int i=0; i<userFollowedTopics.size(); i++) {
//                                Log.d(TAG, "user topic " + i + " : " + userFollowedTopics.get(i).getTopicName());
//                            }
//
//                            successListener.onSuccess(userFollowedTopics);
//                        }
//                        else {
//                            successListener.onSuccess(null);
//                        }
//
//                    }
//                })
                .collection("isFollowing")
                .whereEqualTo("userName", user.getUserName())
                .whereEqualTo("userRole", user.getRole())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots.isEmpty()) {
                            successListener.onSuccess(null);
                            Log.d(TAG, "loading current user followed topic failed !");
                        }
                        else {
                            List<UserTopic> followedTopics = new ArrayList<>();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                followedTopics.add(document.toObject(UserTopic.class));
                            }

                            successListener.onSuccess(followedTopics);
                            Log.d(TAG, "loading current user followed topic successful !");
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "loading current user followed topic failed !");
                        failureListener.onFailure(e);
                    }
                });

    }

    public void loadTutorUsers(User user, List<UserTopic> userFollowedTopics, OnSuccessListener<List<UserTopic>> successListener, OnFailureListener failureListener) {

        // Create a reference to the cities collection
//        CollectionReference collectionReferenceUserTopics = dataSource.getFirebaseFirestoreInstance()
//                .collection("following");


        //TODO: first get all the elements in the collection - we get the number of elements

        if(userFollowedTopics != null) {

            Log.d(TAG, "List of topics in loadTutorUsers : ");

            List<String> topicNames = new ArrayList<>();
            for(UserTopic userTopic: userFollowedTopics) {
                topicNames.add(userTopic.getTopicName());
                Log.d(TAG, userTopic.getUserName() + " => " + userTopic.getTopicName());
            }

            dataSource.getFirebaseFirestoreInstance().collection("isFollowing")
                    .whereEqualTo("userRole", user.getRole().equalsIgnoreCase("learner") ? "tutor" : "learner")
                    .whereNotEqualTo("userName", user.getUserName())
                    .whereIn("topicName", topicNames)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if(queryDocumentSnapshots.isEmpty()) {
                                Log.d(TAG, "loading current user opposite role users topics is empty !");
                                successListener.onSuccess(null);
                            }
                            else {
                                List<UserTopic> usersTopicsWithOppositeRole = new ArrayList<>();
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    usersTopicsWithOppositeRole.add(document.toObject(UserTopic.class));
                                }

                                successListener.onSuccess(usersTopicsWithOppositeRole);
                                Log.d(TAG, "loading current user opposite role users topics successful !");
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            failureListener.onFailure(e);
                            Log.d(TAG, "loading current user opposite role users topics  failed !");
                        }
                    });

        }


        // Récupérer la référence à la collection Firestore
        // CollectionReference collectionRef = dataSource.getFirebaseFirestoreInstance().collection("following");

        // Utiliser la méthode get() pour récupérer un objet QuerySnapshot
//        if(collectionRef != null) {
//
//            collectionRef.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    QuerySnapshot querySnapshot = task.getResult();
//                    // Récupérer le nombre de documents dans la QuerySnapshot
//                    int nombre_documents = querySnapshot.size();
//                    Log.d(TAG, "Nombre de documents : " + nombre_documents);
//                    Log.d(TAG, "documents : " + task.getResult().getDocuments());
//                } else {
//                    Log.d(TAG, "Erreur lors de la récupération des documents : ", task.getException());
//                }
//            });
//
//        }


        // Log.d(TAG, "collection path : " + collectionRef.document(username).getPath());

//        Query query = collectionRef.whereNotEqualTo("field", username);
//        AggregateQuery countQuery = query.count();

        // count the number of docs in a collection. it works !
//        AggregateQuery countQuery = collectionRef.count();
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                AggregateQuerySnapshot snapshot = task.getResult();
//                Log.d(TAG, "Count: " + snapshot.getCount());
//            } else {
//                Log.d(TAG, "Count failed: ", task.getException());
//            }
//        });

        // Récupérer tous les documents dans la collection
//        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            int count = queryDocumentSnapshots.size();
//            Log.d(TAG, "Count: " + count);
//        }).addOnFailureListener(e -> {
//            Log.d(TAG, "Count failed: ", e);
//        });


//        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot querySnapshot) {
//                int count = querySnapshot.size();
//                Log.d("Firestore", "Le nombre de documents est : " + count);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e("Firestore", "Erreur lors de la récupération des documents", e);
//            }
//        });



//        collectionRef.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()) {
//                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
//                                Log.d(TAG, "document id : " + documentSnapshot.getId());
//                            }
//                        }
//                    }
//                });


        // can't count collection's documents
//        dataSource.getFirebaseFirestoreInstance()
//                .collection("following")
//                .count()
//                .get(AggregateSource.SERVER)
//                .addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            // Count fetched successfully
//                            AggregateQuerySnapshot snapshot = task.getResult();
//                            Log.d(TAG, "Count: " + snapshot.getCount());
//                            Log.d(TAG, "nombre d'utiliseurs qui suivent un sujet : " + task.getResult().getCount());
//                        } else {
//                            Log.d(TAG, "Count failed: ", task.getException());
//                        }
//                    }
//                });
//
//
//        if(userFollowedTopics != null) {
//
//            for(UserTopic userTopic : userFollowedTopics) {
//
//                dataSource.getFirebaseFirestoreInstance().collectionGroup("isFollowing")
////                        .whereNotEqualTo("user", userTopic.getUser())
//                        .whereEqualTo("topic", userTopic.getTopic())
//                        //.whereIn("topic", Arrays.asList(userFollowedTopics))
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                // ...
//
//                                List<UserTopic> userTutorFollowedTopics = new ArrayList<>();
//
//                                if(queryDocumentSnapshots != null) {
//
//                                    // exception index out of bounds
//                                    // Log.d(TAG, "list of users with the same topics : " + queryDocumentSnapshots.getDocuments().get(0).getData().get("user"));
//
//                                    Log.d(TAG, "list of users with the same topics : ");
//
//                                    for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
//                                        if(documentSnapshot.exists()) {
//                                            UserTopic followedTopic = documentSnapshot.toObject(UserTopic.class);
//
//                                            if(followedTopic.getUser().getRole().equalsIgnoreCase("tutor")) {
//                                                userTutorFollowedTopics.add(followedTopic);
//                                            }
//
//                                            Log.d(TAG, "user topic names: " + followedTopic.getUser().getUserName() + " " + followedTopic.getTopic().getName());
//                                        }
//
//                                        // duplicated
////                                        UserTopic followedTopic = documentSnapshot.toObject(UserTopic.class);
////                                        userTutorFollowedTopics.add(followedTopic);
////                                        Log.d(TAG, "user topic names: " + followedTopic.getUser().getUserName() + " " + followedTopic.getTopic().getName());
////                                        Log.d(TAG, "user topic names: " + documentSnapshot.getData());
//
//                                    }
//
//                                    for(int i=0; i<userTutorFollowedTopics.size(); i++) {
//                                        Log.d(TAG, "user topic user role : " + i + " : " + userTutorFollowedTopics.get(i).getUser().getRole());
//                                    }
//
//                                    successListener.onSuccess(userTutorFollowedTopics);
//                                }
//                                else {
//                                    // successListener.onSuccess(null);
//                                    Log.d(TAG, "none of the tutors is following the same topics");
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                failureListener.onFailure(e);
//                                Log.d(TAG, "failed to load tutor same UserTopic");
//                            }
//                        });
//
//            }
//
//        }
//        else{
//            Log.d(TAG, "The followed topics list is empty !");
//        }

    }

    public void loadCurrentUser(String currentUsername, OnSuccessListener<User> successListener, OnFailureListener failureListener) {

        // doesn't work!
//        String username = "";
//
//        if(dataSource.getFirebaseAuthInstance() != null)
//            username = dataSource.getFirebaseAuthInstance().getCurrentUser().getDisplayName();
//
//        Log.d(TAG, "transform datasource current username : " + username);

        if(currentUsername != null) {
            dataSource.getFirebaseFirestoreInstance()
                    .collection("users")
                    .document(currentUsername)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()) {
                                successListener.onSuccess(documentSnapshot.toObject(User.class));
                                Log.d(TAG, "user retrieve successful");
                            }
                            else {
                                Log.d(TAG, "Document doesn't exist !");
                            }
                        }
                    })
                    .addOnFailureListener(failureListener);
        }

    }

}
