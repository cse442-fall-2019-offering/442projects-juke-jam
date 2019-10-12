package com.example.ttt.jukejam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Saved_State_Values), Context.MODE_PRIVATE);
        String joinCode = sharedPreferences.getString(getString(R.string.Saved_State_Join_Code),null);
        boolean isDJ = sharedPreferences.getBoolean(getString(R.string.Saved_State_Is_DJ),false);
        if(joinCode != null && isDJ){
            Intent i = new Intent(this, DJActivity.class);
            startActivity(i);
        }
        else if(joinCode != null && !isDJ){
            Intent i = new Intent(this, GuestActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_main);
        MainFragment mainFrag = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,mainFrag);
        ft.commit();

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View rootView = super.onCreateView(name, context, attrs);
       // setupUI(rootView);
        return rootView;
    }



    public void setupUI(View rootView){
        //fragment_container = findViewById(R.id.fragment_container);
        MainFragment mainFrag = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,mainFrag);
        ft.commit();
        Log.d("MainActivity", "got here end of setupUI");
    }
}
