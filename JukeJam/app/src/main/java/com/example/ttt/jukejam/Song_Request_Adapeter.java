package com.example.ttt.jukejam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Song_Request_Adapeter extends ArrayAdapter<SongModel> {

    public Song_Request_Adapeter(Context context, ArrayList<SongModel> friends) {
        super(context,0,friends);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_request_fragment,parent,false);
        }
        TextView name = listItemView.findViewById(R.id.songName);
        TextView artist = listItemView.findViewById(R.id.artist);
        TextView upVotes = listItemView.findViewById(R.id.numUpVotes);
        SongModel request = getItem(position);
        name.setText(request.getTitle());
        artist.setText(request.getArtist());
        upVotes.setText(request.getUpVotes());
        return listItemView;
    }
}

