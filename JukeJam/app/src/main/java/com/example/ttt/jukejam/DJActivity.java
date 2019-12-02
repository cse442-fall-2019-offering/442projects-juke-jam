package com.example.ttt.jukejam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DJActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);
        CreatePartyFragment creatPartyFrag = new CreatePartyFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_dj, creatPartyFrag);
        ft.commit();


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
                //Closes a party and removes it from Firebase.
                FirebaseCommunicator.closeRoom();
                SPAL spal = new SPAL(this);
                spal.clearSharedPrefrences();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.closePartyDev:
                //Won't delete a room from Firebase. Use for testing purposes.
                SPAL spal2 = new SPAL(this);
                spal2.clearSharedPrefrences();
                Intent intent2 = new Intent(this,MainActivity.class);
                startActivity(intent2);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void sendToSpotify(String uri){
        Log.d("DJACtivity", "sendToSpotify: got here uri: "+uri);
        if(mSpotifyAppRemote==null){

        }
        mSpotifyAppRemote.getPlayerApi().queue(uri);
    }

}
