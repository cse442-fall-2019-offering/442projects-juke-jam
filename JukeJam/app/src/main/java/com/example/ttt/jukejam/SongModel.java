package com.example.ttt.jukejam;

public class SongModel {
    private String title;
    private String artist;
    private String uri;
    private int upVotes;
    //private String albumCoverUrl;

    public SongModel(String t, String a, String u){
        title = t;
        artist = a;
        uri = u;
        upVotes=0;
        //albumCoverUrl = ac;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getUri(){
        return uri;
    }

    public int getUpVotes(){
        return upVotes;
    }

    public void upVote(){
        upVotes++;
    }
}
