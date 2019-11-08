package com.example.ttt.jukejam;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.ttt.jukejam.CreatePartyFragment.ROOM_NAME;


public class DJFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Song_Request_Adapeter adapter;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
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


////        FirebaseCommunicator.DJListener(DAL.hashedCode);
//        try
//        {
//            Thread.sleep(3000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }
//        Song_Request_Adapeter.reAssignAndSortData();
////        adapter = new Song_Request_Adapeter(getContext(), Queue.approvalQueue);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dj, container, false);
//        listView = v.findViewById(R.id.songRequestsLV);
//        listView.setAdapter(adapter);
        String Name = getArguments().getString(ROOM_NAME);
        TextView roomNameTV = (TextView) v.findViewById(R.id.roomName);

        mAdapter = new MyAdapter(getFragmentManager());
        mViewPager =  v.findViewById(R.id.DJViewPager);
        mViewPager.setAdapter(mAdapter);
        TabLayout tabLayout = v.findViewById(R.id.DJFragTabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        roomNameTV.setText(Name);
        setupUI(v);
        setupListeners();
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

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
    public static void  updateData() {
//        adapter.clear();
//        ArrayList<SongModel> tempArray = new ArrayList<SongModel>(Queue.approvalQueue);
//        Collections.sort(tempArray, new SongComparator());
//        adapter.addAll(tempArray);
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();


    }
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return new DJRequestListFragment();
            else return new DJFragmentQueue();
        }
        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Request List";
                case 1:
                    return "Queue";
                default:
                    return null;
            }
        }
    }



}
