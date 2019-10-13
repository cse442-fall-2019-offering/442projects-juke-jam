package com.example.ttt.jukejam;

import java.util.Comparator;

public class SongComparator implements Comparator<SongModel> {
    @Override
    public int compare(SongModel s1, SongModel s2) {
        return new Integer(s2.getUpVotes()).compareTo(new Integer(s1.getUpVotes()));
    }
}