package com.example.ttt.jukejam;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAL {

    public static final String DAL_Rooms =  "rooms";
    public static final String DAL_Join_Code_Field =  "join_code";
    public static final String DAL_Room_Name_Field =  "room_name";
    public static final String DAL_Songs_Field = "songs";
    public static final String DAL_Artist_Map_Key = "Artist";
    public static final String DAL_Song_Name_Map_Key = "Song_Name";
    public static final String DAL_URI_Map_Key = "URI";
    public static String hashedCode;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DAL(){

    }

    public void createRoom(String name, String joinCode){
        hashedCode = ""+joinCode.hashCode();
        FirebaseCommunicator.setRoomCode(joinCode);
        FirebaseCommunicator.setRoomName(name);
        FirebaseCommunicator.sendData(Queue.approvalQueue);
        //Map<String, Object> room = new HashMap<>();
        //room.put(DAL_Join_Code_Field, joinCode);
        //room.put(DAL_Room_Name_Field, name);
        //ArrayList<Map<String,String>>  songs = new ArrayList<>();
        //room.put(DAL_Song_Name_Map_Key,songs);
        //Room room = new Room(joinCode, name, Queue.songQueue);
        //db.collection(DAL_Rooms).document(""+joinCode.hashCode()).set(room);
    }


}
