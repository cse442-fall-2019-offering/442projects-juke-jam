package com.example.ttt.jukejam;

import android.provider.Telephony;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class FirebaseCommunicator{
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String roomName;
    private static String roomCode;
    public static int _roomExists;

    public FirebaseCommunicator(){

    }

    public FirebaseCommunicator(Room room){
        CollectionReference collection = db.collection("rooms");
        collection.document(DAL.hashedCode).set(room);
    }

    public static void listenToDocument(String joinCode) {
        // [START listen_document]
        Log.d("DataFetch", joinCode);
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
                    ArrayList<HashMap> temp = (ArrayList<HashMap>) snapshot.get("songs");
                    Queue.approvalQueue = Queue.hashMapToQueue(temp);
                    if (Queue.num == 1) {
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
                if(snapshot.get("joinCode")==null){
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

    public static void checkRoom(String code){
        final DocumentReference docRef = db.collection("rooms").document(code);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        _roomExists = 1;
                        Log.d("joiner", "DocumentSnapshot data: " + document.getData());
                    } else {
                        _roomExists = 0;
                        Log.d("joiner", "No such document");
                    }
                } else {
                    Log.d("joiner", "get failed with ", task.getException());
                }
            }
        });
    }

    public static int roomExists() {
        int retVal = _roomExists;
        _roomExists = -1;
        Log.d("joiner retVal",""+retVal);
        return retVal;
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
