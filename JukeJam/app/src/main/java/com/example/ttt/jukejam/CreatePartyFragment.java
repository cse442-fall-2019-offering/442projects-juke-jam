package com.example.ttt.jukejam;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Space;
import android.widget.Toast;


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
    public static final String ROOM_NAME = "Room Name";
    public static final String JOIN_CODE = "Join Code";
    // TODO: Rename and change types of parameters
    private FirebaseCommunicator fc = new FirebaseCommunicator();
    private String mParam1;
    private String mParam2;
    private EditText partyCodeField;
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
        partyCodeField = rootView.findViewById(R.id.partyCodeField);
        jamBtn = rootView.findViewById(R.id.jamBtn);
    }

    public void setupListeners(){
        partyCodeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String joinCode = partyCodeField.getText().toString();
                String hashCode = ""+joinCode.hashCode();
                fc.checkRoom(hashCode);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String joinCode = partyCodeField.getText().toString();
                String hashCode = ""+joinCode.hashCode();
                fc.checkRoom(hashCode);

            }
        });
        jamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fc.roomExists() == 0){
                    DJFragment djFrag = new DJFragment();
                    DAL dal = new DAL();
                    EditText partyNameET = getActivity().findViewById(R.id.partyName);
                    EditText partyCodeET = getActivity().findViewById(R.id.partyCodeField);
                    dal.createRoom(partyNameET.getText().toString(),partyCodeET.getText().toString());
                    Bundle b = new Bundle();
                    b.putString(ROOM_NAME,partyNameET.getText().toString());
                    b.putString(JOIN_CODE, partyCodeET.getText().toString());
                    djFrag.setArguments(b);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container_dj, djFrag);
                    ft.commit();
                    SPAL spal = new SPAL(getActivity());
                    spal.writeSharedPrefrences(partyCodeET.getText().toString(),true,partyNameET.getText().toString());
                }
                else{
                    Toast t = Toast.makeText(getContext(), "Join code already in use. Please try another.", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
    }

}
