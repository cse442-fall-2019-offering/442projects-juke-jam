package com.example.ttt.jukejam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Queue creation = new Queue();
        super.onCreate(savedInstanceState);
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
