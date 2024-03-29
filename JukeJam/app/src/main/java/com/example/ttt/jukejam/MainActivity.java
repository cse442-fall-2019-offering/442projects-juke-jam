package com.example.ttt.jukejam;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Headers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends FragmentActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback
{
    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "4c9fbb8dc6a2473bbd3afff15404170f";

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "https://example.com";

    private Player mPlayer;
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 1337;
    public String ACCESS_TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFrag = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,mainFrag);
        ft.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                ACCESS_TOKEN = response.getAccessToken();
                SpotifyViewModel model = ViewModelProviders.of(this).get(SpotifyViewModel.class);

                Log.d("Token: ", ACCESS_TOKEN);
                model.getToken().postValue(ACCESS_TOKEN);

                //Shared Pref test
                SharedPreferences sharedPref = this.getSharedPreferences("Saved Token",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(("ACCESS_TOKEN"), ACCESS_TOKEN);
                editor.apply();

                //Content Provider test
//                ContentResolver cr = getContentResolver();
//                Uri uri = buildUri("content", "com.example.ttt.jukejam.provider");
//                ContentValues cv = new ContentValues();
//                cv.put("key","ACCESS_TOKEN");
//                cv.put("value",ACCESS_TOKEN);
//                cr.insert(uri,cv);

                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addNotificationCallback(MainActivity.this);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    public void setupUI(View rootView){
        //fragment_container = findViewById(R.id.fragment_container);
        MainFragment mainFrag = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,mainFrag);
        ft.commit();
        Log.d("MainActivity", "got here end of setupUI");
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error var1) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
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
                        //connected();

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

    private void connected() {
        // Play a playlist
        //mSpotifyAppRemote.getPlayerApi().play("spotify:track:2PpruBYCo4H7WOBJ7Q2EwM");

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.spotify.com/v1/search?q=tania%20bowra&type=artist"; //-H \"Authorization: Bearer "+ACCESS_TOKEN+"\"";
        Log.d("URL", url);
        Log.d("Connected", "Token when mAuthHeader is set: "+ACCESS_TOKEN);
        Headers mAuthHeader = Headers.of("Authorization", "Bearer " + ACCESS_TOKEN);
        Request request = new Request.Builder().get().headers(mAuthHeader).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Got here", "got here response failed");

                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    String searchResults = response.body().string();
                    Log.d("Got here", "got here response successful");
                    Log.d("Search Results", searchResults);
                }
                else{
                    Log.d("Got here", "got here response NOT successful");
                    Log.d("response not successful", "token: "+ACCESS_TOKEN);
                    String searchResults = response.body().string();
                    Log.d("Search Results", searchResults);
                }
            }
        });
//        String command = "curl -X GET \"https://api.spotify.com/v1/search?q=tania%20bowra&type=artist\" -H \"Authorization: Bearer "+ACCESS_TOKEN+"\"";
//        try {
//            Log.d("Connected", "got here 1: ");
//            Process process = Runtime.getRuntime().exec(command);
//            final BufferedReader br = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            Log.d("Connected", "got here 2: ");
//
//            String searchResults = "";
//            StringBuffer sb = new StringBuffer();
//            while ((searchResults = br.readLine()) != null) {
//                Log.d("Connected", "got here 3: ");
//                sb.append(searchResults + "\n");
//                Log.d("Connected","\n" +searchResults);
//            }
//            br.close();
//
//            //Log.d("Search Results", searchResults);
//            Log.d("Connected", "got here 4: ");
//
//
//        } catch (IOException e) {
//            Log.d("Connected", "got here 5: ");
//
//            e.printStackTrace();
//        }
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    private Uri buildUri(String scheme, String authority) {
        //copied from onPTestClickListener
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }
}
