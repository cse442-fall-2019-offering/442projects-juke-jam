package com.example.ttt.jukejam;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestJukeJam {
    ArrayList arr = new ArrayList();

    /**
     * Testing our sorting comparator based on upvotes
     */
    @Test
    public void testSort(){
        SongModel s0 = new SongModel("title1", "artist1", "uri");
        arr.add(s0);
        SongModel s1 = new SongModel("title2", "artist2", "uri");
        arr.add(s1);
        SongModel s2 = new SongModel("title3", "artist3", "uri");
        arr.add(s2);
        for(int i = 0; i < 20; i ++) s0.upVote();
        for(int i = 0; i < 5; i ++) s1.upVote();
        for(int i = 0; i < 21; i ++) s2.upVote();
        Collections.sort(arr, new SongComparator());
        assertTrue(arr.get(0).equals(s2));
        assertTrue(arr.get(1).equals(s0));
        assertTrue(arr.get(2).equals(s1));
    }

    /**
     * Testing function to find and return song in Queue
     */
    @Test
    public void testFindSong(){
        SongModel s0 = new SongModel("title1", "artist1", "uri");
        arr.add(s0);
        Queue q = new Queue();
        assertTrue(s0.equals(q.findSongInQueue("title1","artist1", arr)));
    }

    /**
     * Testing adding a song to our queue
     */
    @Test
    public void testQueueAddition(){
        Queue q = new Queue();
        SongModel s0 = new SongModel("title1", "artist1", "uri");
        q.addSongToSongQueue(s0);
        assertTrue(q.songQueue.get(0).equals(s0));
    }

    /**
     * Testing conversion from map to queue
     */
    @Test
    public void testMapToQueue(){
        Queue q = new Queue();
        HashMap map = new HashMap();
        map.put("title", "title1");
        map.put("artist", "artist1");
        map.put("uri", "testing.com");
        map.put("upVotes", 20);
        ArrayList<HashMap> arr = new ArrayList<HashMap>();
        arr.add(map);
        ArrayList<SongModel> songs = q.hashMapToQueue(arr);
        assertEquals("title1",songs.get(0).getTitle());
        assertEquals("artist1", songs.get(0).getArtist());
        assertEquals(20, songs.get(0).getUpVotes());
    }
}