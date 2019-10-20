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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Search_Adapter extends ArrayAdapter<SongModel> {
    public Search_Adapter(Context context, ArrayList<SongModel> songs){
        super(context, 0, songs);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.search_fragment,parent,false);
        }
        TextView name = listItemView.findViewById(R.id.songName);
        TextView artist = listItemView.findViewById(R.id.artist);
        final SongModel request = getItem(position);
        name.setText(request.getTitle());
        artist.setText(request.getArtist());
        View.OnClickListener addSong = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String tag = (String) view.getTag();
                Toast.makeText(getContext(), request.getTitle()+" Added", Toast.LENGTH_LONG).show();
                Search_Adapter.this.remove(getItem(position));
                Queue.approvalQueue.add(getItem(position));
                FirebaseCommunicator.sendData(Queue.approvalQueue);
            }
        };

        ImageButton addSongIB = listItemView.findViewById(R.id.addSong);
        ImageButton deleteRequestIB = listItemView.findViewById(R.id.deleteRequest);
        addSongIB.setOnClickListener(addSong);


        return listItemView;
    }
}
