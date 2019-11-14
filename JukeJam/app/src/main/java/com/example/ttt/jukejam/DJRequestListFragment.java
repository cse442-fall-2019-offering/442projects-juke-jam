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

public class DJRequestListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Song_Request_Adapeter adapter;
    private static ListView listView;
    private ImageButton searchBtn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DJRequestListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseCommunicator.DJListener(DAL.hashedCode);
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        adapter = new Song_Request_Adapeter(getContext(), Queue.requestList);
        Queue.setRequestList(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.dj_list_views, container, false);
        listView = v.findViewById(R.id.DJListView);
        listView.setAdapter(adapter);
        Queue.setRequestList(adapter);
        return v;
    }
//

}
