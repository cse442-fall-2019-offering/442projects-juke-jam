package com.example.ttt.jukejam;

import android.provider.Telephony;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class FirebaseCommunicator {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String roomName;
    private static String roomCode;

    public FirebaseCommunicator(Room room){
        CollectionReference collection = db.collection("rooms");
        collection.document(DAL.hashedCode).set(room);
    }

    public static void listenToDocument(String joinCode) {
        // [START listen_document]
        final DocumentReference docRef = db.collection("rooms").document(joinCode);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                   Log.w("DataFetch", "Listen failed.", e);
                    return;
                }

                setRoomCode(snapshot.get("joinCode").toString());
                setRoomName(snapshot.get("roomName").toString());
                ArrayList<HashMap> temp= (ArrayList<HashMap>) snapshot.get("songs");
                Queue.approvalQueue = Queue.hashMapToQueue(temp);
                if(Queue.num == 1) {
                    PartyFragment.updateData();
                }

            }
        });
        // [END listen_document]
    }

    public static void DJListener(String joinCode) {
        // [START listen_document]
        if(joinCode==null) {
            joinCode="nojoincode";
        }
        final DocumentReference docRef = db.collection("rooms").document(joinCode);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("DataFetch", "Listen failed.", e);
                    return;
                }

                setRoomCode(snapshot.get("joinCode").toString());
                setRoomName(snapshot.get("roomName").toString());
                ArrayList<HashMap> temp= (ArrayList<HashMap>) snapshot.get("songs");
                Queue.approvalQueue = Queue.hashMapToQueue(temp);
                DJFragment.updateData();
            }
        });
    }

    public static void sendData(ArrayList<SongModel> tempArray){
        Room room = new Room(roomCode, roomName, tempArray);

        //TODO fix nullpointer here
        db.collection("rooms").document(""+roomCode.hashCode()).set(room);
    }

    public static void setRoomName(String str){
        roomName = str;
    }

    public static void setRoomCode(String str){
        roomCode = str;
    }
}
