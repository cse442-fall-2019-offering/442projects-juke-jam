package com.example.ttt.jukejam;

import android.util.Log;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpotifyViewModel extends ViewModel {
    private MutableLiveData<String> token;
    private MutableLiveData<SpotifyAppRemote> spotifyAppRemote;
    public MutableLiveData<String> getToken() {

        if (token == null) {
            token = new MutableLiveData<String>();
            //setToken("");
        }
        Log.d("ViewModel", "getToken: "+token.getValue());
        return token;//.getValue();
    }
//    public LiveData<SpotifyAppRemote> getAppRemote() {
//        return spotifyAppRemote;
//    }
    public void setToken(String t){
        Log.d("ViewModel", "setToken input: "+t);

        if (token == null) {
            token = new MutableLiveData<String>();
        }

        token.postValue(t);
        Log.d("ViewModel", "setToken: "+token.getValue());

    }

}
