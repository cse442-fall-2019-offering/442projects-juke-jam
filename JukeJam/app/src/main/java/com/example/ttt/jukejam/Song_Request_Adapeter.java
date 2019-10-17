package com.example.ttt.jukejam;

import android.content.Context;
import android.media.Image;
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

import com.google.firebase.firestore.FirebaseFirestore;

public class Song_Request_Adapeter extends ArrayAdapter<SongModel> {

    public Song_Request_Adapeter(Context context, ArrayList<SongModel> friends) {
        super(context,0,friends);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_request_fragment,parent,false);
        }
        TextView name = listItemView.findViewById(R.id.songName);
        TextView artist = listItemView.findViewById(R.id.artist);
        TextView upVotes = listItemView.findViewById(R.id.numUpVotes);
        final SongModel request = getItem(position);
        name.setText(request.getTitle());
        artist.setText(request.getArtist());
        upVotes.setText("" + request.getUpVotes());
        View.OnClickListener addSong = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String tag = (String) view.getTag();
                Toast.makeText(getContext(), request.getTitle()+"Added", Toast.LENGTH_LONG).show();
                Song_Request_Adapeter.this.remove(getItem(position));
                Queue.addSongToSongQueue(request);
                Collections.sort(Queue.songQueue, new SongComparator());

                //String joinCode = "1234";
                //String roomName = "aiden";
                FirebaseCommunicator.sendData(Queue.approvalQueue);

                //FirebaseFirestore db = FirebaseFirestore.getInstance();
                //Room room = new Room(joinCode, roomName, Queue.songQueue);
                //db.collection("rooms").document(DAL.hashedCode).set(room);
                //Room room = new Room(joinCode, roomName, Queue.songQueue);
                //FirebaseCommunicator fbc = new FirebaseCommunicator(room);
            }
        };
        View.OnClickListener removeSong = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getContext(), request.getTitle()+"Removed", Toast.LENGTH_LONG).show();
                Song_Request_Adapeter.this.remove(getItem(position));
                Queue.removeSongFromApprovalQueue(request);
                //removeFromFirebase
            }
        };
        ImageButton addSongIB = listItemView.findViewById(R.id.addSong);
        ImageButton deleteRequestIB = listItemView.findViewById(R.id.deleteRequest);
        addSongIB.setOnClickListener(addSong);
        deleteRequestIB.setOnClickListener(removeSong);

        return listItemView;
    }
}

