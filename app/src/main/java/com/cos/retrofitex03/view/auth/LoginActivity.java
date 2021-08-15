package com.cos.retrofitex03.view.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.bean.SessionUser;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.LoginDTO;
import com.cos.retrofitex03.controller.UserController;

import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.view.post.PostListActivity;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements InitSettings {

    private static final String TAG = "LoginActivity";

    private Context mContext = LoginActivity.this;
    private UserController userController;

    private EditText tfUsername, tfPassword;
    private Button btnLogin;
    private TextView tvJoinUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initLr();
    }

    @Override
    public void init() {
        tvJoinUs = findViewById(R.id.tvJoinUs);
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void initLr() {
        // 회원가입으로 가기
        tvJoinUs.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext, JoinActivity.class
            );
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v->{
            String username = tfUsername.getText().toString().trim();
            String password = tfPassword.getText().toString().trim();


            // 공백있을 시 로그인 불가
            if (username.equals("") || password.equals("")){
                MyToast myToast = new MyToast();
                myToast.toast(mContext, "아이디와 비밀번호룰 입력해주세요");
            }
            userController = new UserController();
            userController.login(new LoginDTO(username, password)).enqueue(new Callback<CMRespDTO<User>>() {
                @Override
                public void onResponse(Call<CMRespDTO<User>> call, Response<CMRespDTO<User>> response) {
                    CMRespDTO<User> cm = response.body();
                    SessionUser.user = cm.getData();
                    SessionUser.token = response.headers().get("Authorization");
                    //Log.d(TAG, "onResponse: user : " + SessionUser.user.getUsername());
                    //Log.d(TAG, "onResponse: token : " + SessionUser.token);

                    if (response.body().getCode() == 1){
                        Intent intent = new Intent(mContext, PostListActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<CMRespDTO<User>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                    MyToast.toast(mContext, t.getMessage());
                }
            });
        });
    }

    @Override
    public void initSetting() {

    }
}