package com.cos.retrofitex03.view.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.bean.SessionUser;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostWriteActivity extends CustomAppBarActivity implements InitSettings {

    private static final String TAG = "PostWriteActivity";
    private Context mContext = PostWriteActivity.this;

    private PostController postController = new PostController();

    private EditText tfTitle, tfWriter, tfContent;
    private Button btnWrite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);


        init();
        initLr();
        initSetting();
        initData();
    }

    @Override
    public void init() {
        tfTitle = findViewById(R.id.tfTitle);
        tfWriter = findViewById(R.id.tfWriter);
        tfContent = findViewById(R.id.tfContent);
        btnWrite = findViewById(R.id.btnWrite);

    }

    @Override
    public void initLr() {
        btnWrite.setOnClickListener(v->{
            String title = tfTitle.getText().toString();
            String content = tfContent.getText().toString();

            Post post = Post.builder().title(title).content(content).build();

            postController.insert(SessionUser.token, post).enqueue(new Callback<CMRespDTO<Post>>() {
                @Override
                public void onResponse(Call<CMRespDTO<Post>> call, Response<CMRespDTO<Post>> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    Post writtenPost = response.body().getData();
                    Log.d(TAG, "onResponse: 결과 : " + writtenPost.getTitle());


                    Intent intent = new Intent(mContext, PostDetailActivity.class);
                    intent.putExtra("postId", writtenPost.getId());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<CMRespDTO<Post>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                    MyToast.toast(mContext, "통신 실패");
                }
            });

        });

    }

    @Override
    public void initSetting() {
        settingToolBar("post", true);
    }

    @Override
    public void initData() {
        tfWriter.setText(SessionUser.user.getUsername());
    }
}