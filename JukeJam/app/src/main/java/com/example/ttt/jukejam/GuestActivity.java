package com.example.ttt.jukejam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_guest);
        if(bundle !=null){

            if(bundle.getBoolean(getString(R.string.Join_Room_Extra),false)){
                Log.d("JJDEBUGGuestActivity", "created party frag");
                PartyFragment partyFragment = new PartyFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_guest,partyFragment);
                ft.commit();
            }
            else{
                Log.d("JJDEBUGGuestActivity", "join party frag created");
                JoinPartyFragment joinPartyFrag = new JoinPartyFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_guest,joinPartyFrag);
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
            case R.id.leaveParty:
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
        inflater.inflate(R.menu.menu_guest, menu);
        return true;
    }


}
