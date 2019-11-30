package com.example.ttt.jukejam;

import android.util.Log;
import android.widget.ArrayAdapter;

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
    public static ArrayList<SongModel> upVotedSongs = new ArrayList<SongModel>();
    public static ArrayList<SongModel> downVotedSongs = new ArrayList<SongModel>(10);
    public static Song_Request_Adapeter request_adapeter;
    public static Queue_ArrayAdapter queue_arrayAdapter;
    private static boolean autoQueue = false;

    public Queue(){
        SongModel s = new SongModel("Hey ya!","Outkast","");
        for(int i=0;i<10;i++) s.upVote();
        //songQueue.add(s);
        //requestList.add(s);

        s = new SongModel("Never Gonna Give You Up","Rick Astley","");
        for(int i=0;i<15;i++) s.upVote();
        //songQueue.add(s);
        //requestList.add(s);

        s = new SongModel("All Star","Smash Mouth","");
        for(int i=0;i<3;i++) s.upVote();
        //songQueue.add(s);
        //requestList.add(s);

        s = new SongModel("To Be Approved","Approve me","");
        for(int i=0;i<4;i++) s.upVote();
        //requestList.add(s);
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
//        for(SongModel m : Queue.requestList) Log.v("Backend Int", ""+ m.getTitle());
        songQueue.add(song);
        requestList.remove(song);
        updateViews();
    }

    public static void removeSongFromApprovalQueue(SongModel song){
        requestList.remove(song);
        updateViews();
    }

    public static ArrayList<SongModel> hashMapToQueue(ArrayList<HashMap> tempArray){
        ArrayList<SongModel> futueQueue = new ArrayList<SongModel>();

        for(int i = 0; i<tempArray.size(); i++) {
            String uri;
            try{ uri = tempArray.get(i).get("uri").toString(); }
            catch(Exception e){ uri = ""; }
            SongModel s = new SongModel(tempArray.get(i).get("title").toString(), tempArray.get(i).get("artist").toString(), uri);
            s.setUpVotes(Integer.parseInt(tempArray.get(i).get("upVotes").toString()));
            futueQueue.add(s);
        }

        return futueQueue;
    }

    public static boolean upVoteCheck(SongModel song){
        SongModel temp = findSongInQueue(song.getTitle(),song.getArtist(),downVotedSongs);
        if(downVotedSongs.contains(temp)){
            downVotedSongs.remove(temp);
            return false;
        }

        temp = findSongInQueue(song.getTitle(),song.getArtist(),upVotedSongs);
        if(upVotedSongs.contains(temp))return true;
        else {
            upVotedSongs.add(song);
            return false;
        }
    }

    public static boolean downVoteCheck(SongModel song){
        SongModel temp = findSongInQueue(song.getTitle(),song.getArtist(),upVotedSongs);
        if(upVotedSongs.contains(temp)){
            upVotedSongs.remove(temp);
            return false;
        }

        temp = findSongInQueue(song.getTitle(),song.getArtist(),downVotedSongs);
        if(downVotedSongs.contains(temp))return true;
        else{
            downVotedSongs.add(song);
            return false;
        }
    }
    public static void setRequestListAdapter(Song_Request_Adapeter r){
         request_adapeter = r;
    }
    public static void setQueueAdapter(Queue_ArrayAdapter q){
        queue_arrayAdapter = q;
    }

    public static void addSongToRequestList(SongModel s){
        requestList.add(s);
        updateViews();
        if (autoQueue) approveAll();
    }
    public static void setAutoQueue(boolean aQ){
        autoQueue = aQ;
        if(aQ) approveAll();
    }
    private static void approveAll(){
        songQueue.addAll(requestList);
        requestList.clear();
        FirebaseCommunicator.sendData(requestList,songQueue);
        updateViews();
    }
    public static void setSongQueue(ArrayList<HashMap> songQ){
        songQueue = hashMapToQueue(songQ);
        updateViews();
    }
    public static void setRequestList(ArrayList<HashMap> requestL){
        requestList = hashMapToQueue(requestL);
        if (autoQueue) approveAll();
    }
    private static void updateViews(){
        if (request_adapeter != null)request_adapeter.reAssignAndSortData();
        if (queue_arrayAdapter != null)queue_arrayAdapter.reAssignAndSortData();
    }


}
