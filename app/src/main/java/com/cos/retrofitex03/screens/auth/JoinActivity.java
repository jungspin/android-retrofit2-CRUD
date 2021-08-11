package com.cos.retrofitex03.screens.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.UserController;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyDialogFragment;
import com.cos.retrofitex03.util.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity implements InitSettings {

    private static final String TAG = "JoinActivity";
    private Context mContext = JoinActivity.this;

    private UserController userController = new UserController();

    private EditText tfUsername, tfPassword, tfEmail;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        initLr();
    }

    @Override
    public void init() {
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        tfEmail = findViewById(R.id.tfEmail);
        btnJoin = findViewById(R.id.btnJoin);

    }

    @Override
    public void initLr() {
        btnJoin.setOnClickListener(v->{
            String username = tfUsername.getText().toString();
            String password = tfPassword.getText().toString();
            String email = tfEmail.getText().toString();

            User user = User.builder().username(username).password(password).email(email).build();

            if (username.equals("") || password.equals("") || email.equals("")){
                MyToast myToast = new MyToast();
                myToast.toast(mContext, "모든 칸을 입력해주세요");
            }
            Call<CMRespDTO> cm = userController.join(user);
            cm.enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                    if (response.body().getCode() == 1){
                        //showNoticeDialog();

                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        MyToast myToast = new MyToast();
                        myToast.toast(mContext, "다시 시도해주세요");
                    }
                }

                @Override
                public void onFailure(Call<CMRespDTO> call, Throwable t) {
                    Log.d(TAG, "onResponse: 통신 실패  : "  + t);
                    MyToast myToast = new MyToast();
                    myToast.toast(mContext, "다시 시도해주세요");
                }
            });
        });

    }

    @Override
    public void initSetting() {

    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new MyDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }
}