package com.example.ttt.jukejam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Queue {

    public static int num = 0;
    public static ArrayList<SongModel> songQueue = new ArrayList<SongModel>(10);        //in-house queue that is regularly changing
    public static ArrayList<SongModel> spotifyQueue = new ArrayList<SongModel>();     //queue that holds current song being played and directly after
    public static ArrayList<SongModel> requestList = new ArrayList<SongModel>(10);    //songs that require DJ approval to add to in-house queue

    public Queue(){
        SongModel s = new SongModel("Hey ya!","Outkast","");
        for(int i=0;i<10;i++) s.upVote();
        songQueue.add(s);
        requestList.add(s);

        s = new SongModel("Never Gonna Give You Up","Rick Astley","");
        for(int i=0;i<15;i++) s.upVote();
        //songQueue.add(s);
        requestList.add(s);

        s = new SongModel("All Star","Smash Mouth","");
        for(int i=0;i<3;i++) s.upVote();
        //songQueue.add(s);
        requestList.add(s);

        s = new SongModel("To Be Approved","Approve me","");
        for(int i=0;i<4;i++) s.upVote();
        requestList.add(s);
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
        requestList.remove(song);
    }

    public static void removeSongFromApprovalQueue(SongModel song){
        requestList.remove(song);
    }

    public static ArrayList<SongModel> hashMapToQueue(ArrayList<HashMap> tempArray){
        ArrayList<SongModel> futueQueue = new ArrayList<SongModel>();

        for(int i = 0; i<tempArray.size(); i++) {
            String uri = tempArray.get(i).get("uri").toString();
            if(uri==null){uri="";}
            SongModel s = new SongModel(tempArray.get(i).get("title").toString(), tempArray.get(i).get("artist").toString(), uri);
            s.setUpVotes(Integer.parseInt(tempArray.get(i).get("upVotes").toString()));
            futueQueue.add(s);
        }

        return futueQueue;
    }

}
