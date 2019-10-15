package com.example.ttt.jukejam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Queue {


    public static ArrayList<SongModel> songQueue = new ArrayList<SongModel>(10);        //in-house queue that is regularly changing
    public static ArrayList<SongModel> spotifyQueue = new ArrayList<SongModel>(3);     //queue that holds current song being played and directly after
    public static ArrayList<SongModel> approvalQueue = new ArrayList<SongModel>(10);    //songs that require DJ approval to add to in-house queue

    public Queue(){
        SongModel s = new SongModel("Hey ya!","Outkast",null);
        for(int i=0;i<10;i++) s.upVote();
        songQueue.add(s);
        //approvalQueue.add(s);

        s = new SongModel("Never Gonna Give You Up","Rick Astley",null);
        for(int i=0;i<15;i++) s.upVote();
        songQueue.add(s);
        //approvalQueue.add(s);

        s = new SongModel("All Star","Smash Mouth",null);
        for(int i=0;i<3;i++) s.upVote();
        //songQueue.add(s);
        approvalQueue.add(s);

        s = new SongModel("To Be Approved","Approve me",null);
        for(int i=0;i<4;i++) s.upVote();
        approvalQueue.add(s);
    }

    public static SongModel findSongInQueue(String title, String artist, ArrayList<SongModel> tempList){
        SongModel foundSong = new SongModel(null,null,null);
        for (SongModel tempSong:tempList) {
            if(tempSong.getTitle().equals(title) && tempSong.getArtist().equals(artist)){
                return tempSong;
            }
        }


        return foundSong;
    }

    public static void addSongToSongQueue(SongModel song){
        songQueue.add(song);
        approvalQueue.remove(song);
    }

    public static void removeSongFromApprovalQueue(SongModel song){
        approvalQueue.remove(song);
    }

}
