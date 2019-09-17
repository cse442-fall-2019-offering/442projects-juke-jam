package com.example.ttt.jukejam;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DJFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DJFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Song_Request_Adapeter adapter;
    private ListView listView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DJFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Song_Request_Adapeter(getContext(), (ArrayList<SongModel>) dummyData());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String roomName = getArguments().getString("Room Name");
        View v = inflater.inflate(R.layout.fragment_dj, container, false);
        listView = v.findViewById(R.id.songRequestsLV);
        listView.setAdapter(adapter);
        return v;
    }
    private List<SongModel> dummyData(){
        List<SongModel> retVal = new ArrayList<SongModel>();
        SongModel s = new SongModel("Hey ya!","Outkast",null);
        for(int i=0;i<10;i++) s.upVote();
        retVal.add(s);
        s = new SongModel("Broken Arrows","Avicii",null);
        for(int i=0;i<5;i++) s.upVote();

        retVal.add(s);
        s = new SongModel("All Star","Smash Mouth",null);
        for(int i=0;i<3;i++) s.upVote();

        retVal.add(s);
        Log.d("PartyFragment", "got here: dummyData: ");
        return  retVal;
    }

}
