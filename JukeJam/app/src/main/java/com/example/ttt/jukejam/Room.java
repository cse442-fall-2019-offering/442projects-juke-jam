package com.example.ttt.jukejam;

import java.util.List;

public class Room {
    public String joinCode, roomName;
    public List<SongModel> songs;

    public Room(){
    }

    public Room(String joinCode, String roomName, List<SongModel> songs) {
        this.joinCode = joinCode;
        this.roomName = roomName;
        this.songs = songs;
    }

}
