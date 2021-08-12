package com.cos.retrofitex03.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class MySharedPreferences {

    public String getSharedData(Context mContext){
        SharedPreferences pref = mContext.getSharedPreferences("myData", Activity.MODE_PRIVATE);
        return pref.getString("token", "");
    }
}
