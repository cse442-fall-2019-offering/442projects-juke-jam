package com.example.ttt.jukejam;

import java.util.List;

public class Room {
    public String joinCode, roomName;
    public List<SongModel> requestList, queue;

    public Room(){
    }

    public Room(String joinCode, String roomName, List<SongModel> requestList, List<SongModel> queue) {
        this.joinCode = joinCode;
        this.roomName = roomName;
        this.requestList = requestList;
        this.queue = queue;
    }

}
