package com.example.ttt.jukejam;

public class SongModel {
    private String title;
    private String artist;
    private int upVotes;
    //private String albumCoverUrl;

    public SongModel(String t, String a, String ac){
        title = t;
        artist = a;
        upVotes=0;
        //albumCoverUrl = ac;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public int getUpVotes(){
        return upVotes;
    }

    public void upVote(){
        upVotes++;
    }

    public void downVote(){ upVotes--; }
}
