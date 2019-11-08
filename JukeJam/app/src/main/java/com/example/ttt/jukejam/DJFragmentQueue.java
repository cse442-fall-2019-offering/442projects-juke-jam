package com.example.ttt.jukejam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;

import static com.example.ttt.jukejam.CreatePartyFragment.ROOM_NAME;

public class DJFragmentQueue extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Queue_ArrayAdapter adapter;
    private static ListView listView;
    private ImageButton searchBtn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DJFragmentQueue() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseCommunicator.DJListener(DAL.hashedCode);
//        try
//        {
//            Thread.sleep(3000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
        Queue_ArrayAdapter.reAssignAndSortData();
        adapter = new Queue_ArrayAdapter(getContext(), Queue.requestList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dj_list_views, container, false);
        listView = v.findViewById(R.id.DJListView);
        listView.setAdapter(adapter);

        return v;
    }
//    private List<SongModel> dummyData(){
//        List<SongModel> retVal = new ArrayList<SongModel>();
//        SongModel s = new SongModel("Hey ya!","Outkast",null);
//        for(int i=0;i<10;i++) s.upVote();
//        retVal.add(s);
//        s = new SongModel("Broken Arrows","Avicii",null);
//        for(int i=0;i<5;i++) s.upVote();
//
//        retVal.add(s);
//        s = new SongModel("All Star","Smash Mouth",null);
//        for(int i=0;i<3;i++) s.upVote();
//
//        retVal.add(s);
//        Log.d("PartyFragment", "got here: dummyData: ");
//        return  retVal;
//    }


//    public static void  updateData() {
//        adapter.clear();
//        ArrayList<SongModel> tempArray = new ArrayList<SongModel>(Queue.approvalQueue);
//        Collections.sort(tempArray, new SongComparator());
//        adapter.addAll(tempArray);
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }
}
