package com.example.ttt.jukejam;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button createPartyBtn;
    private Button joinPartyBtn;
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setupUI(rootView);
        setupListeners();
        return rootView;
    }

    public void setupUI(View rootView){
        createPartyBtn = rootView.findViewById(R.id.createPartyBtn);
        joinPartyBtn = rootView.findViewById(R.id.joinPartyBtn);
        //animated background
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.mainlayout);
        AnimationDrawable aniDraw = (AnimationDrawable) constraintLayout.getBackground();
        aniDraw.setEnterFadeDuration(2000);
        aniDraw.setExitFadeDuration(4000);
        aniDraw.start();
    }

    public void setupListeners(){
        createPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainFragment", "got here create onClick ");
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                CreatePartyFragment newFrag = new CreatePartyFragment();
//                ft.replace(R.id.fragment_container,newFrag);
//                ft.commit();
                Intent intent = new Intent(getActivity(),DJActivity.class);
                startActivity(intent);
            }
        });

        joinPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainFragment", "got here join onClick ");

//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                JoinPartyFragment newFrag = new JoinPartyFragment();
//                ft.replace(R.id.fragment_container,newFrag);
//                ft.commit();
                Intent intent = new Intent(getActivity(),GuestActivity.class);
                startActivity(intent);
            }
        });
    }

}
