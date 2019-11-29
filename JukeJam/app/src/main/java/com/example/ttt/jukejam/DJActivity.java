package com.example.ttt.jukejam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class DJActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "4c9fbb8dc6a2473bbd3afff15404170f";

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "https://example.com";

    private Player mPlayer;
    public SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 1337;
    private String ACCESS_TOKEN;
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

                        // TODO: this is where we wil
                        int qLength;

                        final Player player;
                        mSpotifyAppRemote.getPlayerApi().subscribeToPlayerContext().setEventCallback(new Subscription.EventCallback<PlayerContext>() {
                            @Override
                            public void onEvent(PlayerContext playerContext) {
                                Log.d("Context Event", "onEvent: "+playerContext.subtitle);
                                if(Queue.songQueue.size()>0){
                                    SongModel cur = Queue.songQueue.get(0);
                                    sendToSpotify(cur.getUri());
                                    Queue.songQueue.remove(cur);
                                }

                            }
                        });
                        mSpotifyAppRemote.getPlayerApi().getPlayerState().setResultCallback(playerState -> {
                                    final Track track = playerState.track;
                                    if (track != null) {
                                        Log.d("MainActivity", track.name + " by " + track.artist.name);

                                    }

                                });

//                        cur = Queue.songQueue.get(0);
//                        next = Queue.songQueue.get(1);
                        //sendToSpotify(Queue.getUri());
//                        while(true){
//                            //qLength = Queue.songQueue.size();

//                            final SongModel cur = Queue.songQueue.get(0);
//                            final SongModel next = Queue.songQueue.get(1);
//                            sendToSpotify(cur.getUri());
//                            mSpotifyAppRemote.getPlayerApi().getPlayerState().setResultCallback(playerState -> {
//                                final Track track = playerState.track;
//                                while(track.equals(cur)){
//
//                                }
//                                Queue.songQueue.remove(cur);
//                            });
//                            try{
//                                Thread.sleep(1000);
//                            }
//                            catch (Exception e){
//
//                            }
//                        }

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
