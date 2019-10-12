package com.example.ttt.jukejam;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinPartyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinPartyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button joinRoomBtn;
    public JoinPartyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinPartyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinPartyFragment newInstance(String param1, String param2) {
        JoinPartyFragment fragment = new JoinPartyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_join_party, container, false);
        setupUI(rootView);
        setupListeners();
        return rootView;
    }

    public void setupUI(View rootView){
        joinRoomBtn = rootView.findViewById(R.id.joinRoomBtn);
    }
    public void setupListeners(){
        joinRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartyFragment partyFrag = new PartyFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_guest, partyFrag);
                ft.commit();
                EditText partyCodeET = getActivity().findViewById(R.id.partyName);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.Saved_State_Values), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.Saved_State_Join_Code), partyCodeET.getText().toString());
                editor.putBoolean(getString(R.string.Saved_State_Is_DJ), false);
                editor.commit();
            }
        });
    }

}
