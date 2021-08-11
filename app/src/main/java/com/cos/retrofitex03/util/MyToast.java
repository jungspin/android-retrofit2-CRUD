package com.cos.retrofitex03.util;

import android.content.Context;
import android.widget.Toast;

public class MyToast {



    public void toast(Context mContext, CharSequence text){

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(mContext, text, duration);
        toast.show();
    }


}
