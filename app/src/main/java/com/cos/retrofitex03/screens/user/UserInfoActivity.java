package com.cos.retrofitex03.screens.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.google.gson.Gson;

public class UserInfoActivity extends CustomAppBarActivity implements InitSettings {

    private EditText tfUsername, tfEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        settingToolBar("user" , true);
        init();
        initSetting();
    }

    @Override
    public void init() {
        tfUsername = findViewById(R.id.tfUsername);
        tfEmail = findViewById(R.id.tfEmail);
    }

    @Override
    public void initLr() {

    }

    @Override
    public void initSetting() {
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String userInfo = pref.getString("principal", "");

        Gson gson = new Gson();
        User principal = gson.fromJson(userInfo, User.class);

        tfUsername.setText(principal.getUsername());
        tfEmail.setText(principal.getEmail());
    }

    @Override
    public void initData() {

    }
}