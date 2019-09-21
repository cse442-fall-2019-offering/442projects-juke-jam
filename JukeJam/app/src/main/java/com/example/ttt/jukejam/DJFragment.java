package com.example.ttt.jukejam;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class DJFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Song_Request_Adapeter adapter;
    private ListView listView;
    private ImageButton searchBtn;
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
        View v = inflater.inflate(R.layout.fragment_dj, container, false);
        listView = v.findViewById(R.id.songRequestsLV);
        listView.setAdapter(adapter);
        setupUI(v);
        setupListeners();
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

    public void setupUI(View rootVeiw){
        searchBtn = rootVeiw.findViewById(R.id.searchButton);
    }
    public void setupListeners(){
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
