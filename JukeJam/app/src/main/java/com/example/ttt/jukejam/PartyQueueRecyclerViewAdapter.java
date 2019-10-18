package com.example.ttt.jukejam;
import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
public class PartyQueueRecyclerViewAdapter extends RecyclerView.Adapter<PartyQueueRecyclerViewAdapter.PartyQueueHolder>{
    private static List<SongModel> dataset;
    private static Context context;
//    private OnItemClicked listener;
    private SongModel song;

    public PartyQueueRecyclerViewAdapter(List<SongModel> myDataset, Context context){
        dataset=myDataset;
        Collections.sort(dataset, new SongComparator());
        Log.d("Adapter", "PartyQueueRecyclerViewAdapter: dataset size= "+dataset.size());
        this.context=context;
    }
    @NonNull
    @Override
    public PartyQueueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView v = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_party_queue,parent,false);
        PartyQueueHolder pqh = new PartyQueueHolder(v);
        return pqh;
    }

    @Override
    public void onBindViewHolder(@NonNull PartyQueueHolder holder, int position) {
        final SongModel song = dataset.get(position);
        holder.titleTV.setText(song.getTitle());
        holder.artistTV.setText(song.getArtist());
        holder.upvoteCountTV.setText(String.valueOf(song.getUpVotes()));
//        holder.upvoteCountTV.setText("14");
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: "+dataset.size());
        return dataset.size();
    }

    public static class PartyQueueHolder extends RecyclerView.ViewHolder{
        private TextView titleTV;
        private TextView artistTV;
        private TextView upvoteCountTV;
        //private TextView upvotesTV;
        private ImageButton upvoteBtn;
        private ImageButton downvoteBtn;
        PartyQueueHolder(View itemView){
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            artistTV = itemView.findViewById(R.id.artistTV);
            upvoteBtn = itemView.findViewById(R.id.upvoteBtn);
            downvoteBtn = itemView.findViewById(R.id.downvoteBtn);
            upvoteCountTV = itemView.findViewById(R.id.upvoteCountTV);

            upvoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = (String) titleTV.getText();
                    String artist = (String) artistTV.getText();
                    SongModel upvotedSong = Queue.findSongInQueue(title, artist, Queue.approvalQueue);
                    //int i = Queue.findSongNumInQueue(title, artist, Queue.approvalQueue);
                    upvotedSong.upVote();
                    //Queue.approvalQueue.get(i).upVote();
                    //redraw fragment
                    FirebaseCommunicator.sendData(Queue.approvalQueue);

                    //notifyDataSetChanged();
                    //upvoteCountTV.setText(String.valueOf(Integer.valueOf(upvoteCountTV.getText().toString())+1));
                }
            });

            downvoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = (String) titleTV.getText();
                    String artist = (String) artistTV.getText();
                    SongModel downvotedSong = Queue.findSongInQueue(title, artist, Queue.approvalQueue);
                    downvotedSong.downVote();
                    //redraw fragment
                    FirebaseCommunicator.sendData(Queue.approvalQueue);
                    //upvoteCountTV.setText(String.valueOf(Integer.valueOf(upvoteCountTV.getText().toString())-1));

                }
            });
        }
    }

    public interface OnItemClicked{
        void onItemClick(SongModel model);
    }

    public static void reAssignAndSortData(){
        dataset = Queue.approvalQueue;
        Collections.sort(dataset, new SongComparator());
    }
}
