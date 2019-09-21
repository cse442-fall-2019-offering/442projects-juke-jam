package com.example.ttt.jukejam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listView;
    private Search_Adapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Search_Adapter(getContext(), (ArrayList<SongModel>)dummyData());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String roomName = getArguments().getString("Room Name");
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        listView = v.findViewById(R.id.searchLV);
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
