package com.example.ttt.jukejam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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

    public void joinRoom(){
        boolean isDJ = sharedPreferences.getBoolean(activity.getString(R.string.Saved_State_Is_DJ),false);
        if(isDJ){
            Intent i = new Intent(activity, DJActivity.class);
            activity.startActivity(i);
        }
        else{
            Intent i = new Intent(activity, GuestActivity.class);
            activity.startActivity(i);
        }

    }
    public void writeSharedPrefrences(String joinCode, boolean isDJ){

    }
    public void clearSharedPrefrences(){
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.Saved_State_Values),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

}
