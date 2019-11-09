package com.example.ttt.jukejam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Queue_ArrayAdapter extends ArrayAdapter<SongModel> {
    private static ArrayList<SongModel> dataset;

    public Queue_ArrayAdapter(Context context, ArrayList<SongModel> friends) {
        super(context,0,dataset);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.queue_lv_frag,parent,false);
        }
        TextView name = listItemView.findViewById(R.id.songName);
        TextView artist = listItemView.findViewById(R.id.artist);
        TextView upVotes = listItemView.findViewById(R.id.numUpVotes);
        final SongModel request = getItem(position);
        name.setText(request.getTitle());
        artist.setText(request.getArtist());
        upVotes.setText(""+request.getUpVotes());
        return listItemView;
    }

    public static void reAssignAndSortData(){
        dataset = Queue.requestList;
        Collections.sort(dataset, new SongComparator());
    }
}

