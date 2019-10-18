package com.example.ttt.jukejam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DJActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            setContentView(R.layout.activity_dj);
            if(bundle.getBoolean(getString(R.string.Join_Room_Extra))){
                DJFragment djFragment = new DJFragment();
                SPAL spal = new SPAL(this);
                Bundle b = spal.getBundle();
                djFragment.setArguments(b);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_dj, djFragment);
                ft.commit();
            }
            else{
                CreatePartyFragment creatPartyFrag = new CreatePartyFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_dj, creatPartyFrag);
                ft.commit();
            }
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.closeParty:
                //TODO: remove room from database
                SPAL spal = new SPAL(this);
                spal.clearSharedPrefrences();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}
