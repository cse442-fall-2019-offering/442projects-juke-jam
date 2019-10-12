package com.example.ttt.jukejam;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePartyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePartyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button jamBtn;

    public CreatePartyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePartyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePartyFragment newInstance(String param1, String param2) {
        CreatePartyFragment fragment = new CreatePartyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_create_party, container, false);
        setupUI(rootView);
        setupListeners();
        return rootView;
    }

    public void setupUI(View rootView){
        jamBtn = rootView.findViewById(R.id.jamBtn);
    }

    public void setupListeners(){
        jamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DJFragment djFrag = new DJFragment();
                DAL dal = new DAL();
                EditText partyNameET = getActivity().findViewById(R.id.partyName);
                EditText partyCodeET = getActivity().findViewById(R.id.partyCodeField);
                dal.createRoom(partyNameET.getText().toString(),partyCodeET.getText().toString());
                Bundle b = new Bundle();
                b.putString("Name",partyNameET.getText().toString());
                b.putString("Join Code", partyCodeET.getText().toString());
                djFrag.setArguments(b);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_dj, djFrag);
                ft.commit();
            }
        });
    }

}
