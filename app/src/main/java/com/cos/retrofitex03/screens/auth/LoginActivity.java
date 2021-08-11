package com.cos.retrofitex03.screens.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.LoginDTO;
import com.cos.retrofitex03.controller.UserController;

import com.cos.retrofitex03.screens.post.PostListActivity;
import com.cos.retrofitex03.service.UserService;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements InitSettings {

    private static final String TAG = "LoginActivity";

    private Context mContext = LoginActivity.this;
    private UserController userController = new UserController();



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
            String username = tfUsername.getText().toString();
            String password = tfPassword.getText().toString();

            // 공백있을 시 로그인 불가
            if (username.equals("") || password.equals("")){
                MyToast myToast = new MyToast();
                myToast.toast(mContext, "아이디와 비밀번호룰 입력해주세요");
            }
            Call<CMRespDTO> cm = userController.login(new LoginDTO(username, password));
            cm.enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {

                    if (response.body().getCode() == 1){
                        String token = response.headers().get("Authorization");

                        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", token);

                        Gson gson = new Gson();
                        String bodyToJson = gson.toJson(response.body().getData());
                        editor.putString("principal", bodyToJson);
                        editor.commit();

                        Intent intent = new Intent(mContext, PostListActivity.class);
                        startActivity(intent);
                    } else {
                        MyToast myToast = new MyToast();
                        myToast.toast(mContext, "아이디와 비밀번호를 확인해주세요");
                    }
                }

                @Override
                public void onFailure(Call<CMRespDTO> call, Throwable t) {
                    Log.d(TAG, "onFailure: 통신실패 : " + t.getMessage());
                }
            });
        });
    }

    @Override
    public void initSetting() {

    }
}