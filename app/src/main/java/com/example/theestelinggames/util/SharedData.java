package com.example.theestelinggames.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {

    private SharedPreferences sharedPreferences;

    public SharedData(Context context, String preferenceName) {
        this.sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value){
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
