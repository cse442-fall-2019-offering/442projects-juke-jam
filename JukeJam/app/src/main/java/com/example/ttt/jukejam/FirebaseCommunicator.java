package com.example.ttt.jukejam;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseCommunicator {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseCommunicator(Room room){
        CollectionReference collection = db.collection("rooms");
        collection.add(room);
    }
}
