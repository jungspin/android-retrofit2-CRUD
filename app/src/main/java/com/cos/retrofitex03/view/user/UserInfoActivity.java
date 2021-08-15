package com.cos.retrofitex03.view.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.bean.SessionUser;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.google.gson.Gson;

import java.io.Serializable;

public class UserInfoActivity extends CustomAppBarActivity implements InitSettings {

    private EditText tfUsername, tfEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        init();
        initSetting();
        initData();
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
        settingToolBar("user" , true);
    }

    @Override
    public void initData() {
        tfUsername.setText(SessionUser.user.getUsername());
        tfEmail.setText(SessionUser.user.getEmail());
    }
}