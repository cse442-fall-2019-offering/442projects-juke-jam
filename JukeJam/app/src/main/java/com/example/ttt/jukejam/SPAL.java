package com.example.ttt.jukejam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import static com.example.ttt.jukejam.CreatePartyFragment.JOIN_CODE;
import static com.example.ttt.jukejam.CreatePartyFragment.ROOM_NAME;

public class SPAL {
    //shared prefrences abstraction layer
    SharedPreferences sharedPreferences;
    Activity activity;
    public SPAL(Activity a){
        activity = a;
        sharedPreferences = activity.getSharedPreferences(a.getString(R.string.Saved_State_Values), Context.MODE_PRIVATE);
    }

    public boolean inRoom(){
        return sharedPreferences.getString(activity.getString(R.string.Saved_State_Join_Code),null) != null;
    }

    public boolean getIsDj(){
        return sharedPreferences.getBoolean(activity.getString(R.string.Saved_State_Is_DJ), false);

    }
    public boolean getAutoQueue(){
        return sharedPreferences.getBoolean(activity.getString(R.string.Saved_State_Auto_Queue), false);
    }


    public void joinRoom(){
        boolean isDJ = sharedPreferences.getBoolean(activity.getString(R.string.Saved_State_Is_DJ),false);
//        Log.d("JJDEBUGSPAL","JOINING ROOM");
        if(isDJ){
            Intent i = new Intent(activity, DJActivity.class);
            //true indicates they are in a room
            i.putExtra(activity.getString(R.string.Join_Room_Extra),true);
            activity.startActivity(i);
        }
        else{
            Intent i = new Intent(activity, GuestActivity.class);
            //true indicates they are in a room
//            Log.d("JJDEBUGSPAL","put extra");
            i.putExtra(activity.getString(R.string.Join_Room_Extra),true);
            activity.startActivity(i);
        }

    }
    public void writeSharedPrefrences(String joinCode, boolean isDJ,String roomName, boolean autoQueue){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(activity.getString(R.string.Saved_State_Join_Code), joinCode);
        editor.putString(activity.getString(R.string.Saved_State_Room), roomName);
        editor.putBoolean(activity.getString(R.string.Saved_State_Is_DJ), isDJ);
        editor.putBoolean(activity.getString(R.string.Saved_State_Auto_Queue), autoQueue);
        editor.commit();

    }
    public void clearSharedPrefrences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(JOIN_CODE, sharedPreferences.getString(activity.getString(R.string.Saved_State_Join_Code), ""));
        bundle.putString(ROOM_NAME, sharedPreferences.getString(activity.getString(R.string.Saved_State_Room),""));
        return bundle;
    }

}
