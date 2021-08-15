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
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostUpdateActivity extends CustomAppBarActivity implements InitSettings {

    private static final String TAG = "PostUpdateActivity";
    private Context mContext = PostUpdateActivity.this;

    private PostController postController;

    private EditText tfTitle, tfWriter, tfContent;
    private Button btnUpdate;

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);


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
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    @Override
    public void initLr() {
        btnUpdate.setOnClickListener(v->{
            int postId = getIntent().getIntExtra("postId", 0);

            String title = tfTitle.getText().toString();
            String content = tfContent.getText().toString();

            Post post = Post.builder().title(title).content(content).build();
            postController.updateById(postId, SessionUser.token, post).enqueue(new Callback<CMRespDTO<Post>>() {
                // 왜 자꾸 권한이 없대..? 내가 안써서 권한 없구나..미쳤다 대박
                @Override
                public void onResponse(Call<CMRespDTO<Post>> call, Response<CMRespDTO<Post>> response) {
                    Log.d(TAG, "onResponse: " +response.body());
                    Post updatedPost = response.body().getData();
                    if (response.body().getCode() == 1){

                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("postId", updatedPost.getId());
                        finish();
                        startActivity(intent);
                    } else {
                        MyToast.toast(mContext, response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<CMRespDTO<Post>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });

        });




    }

    @Override
    public void initSetting() {
        settingToolBar("update", true);
    }

    @Override
    public void initData() {
        int postId = getIntent().getIntExtra("postId", 0);
        //Log.d(TAG, "initData: " + postId);
        postController = new PostController();
        postController.findById(postId, SessionUser.token).enqueue(new Callback<CMRespDTO<Post>>() {
            @Override
            public void onResponse(Call<CMRespDTO<Post>> call, Response<CMRespDTO<Post>> response) {
                Post post = response.body().getData();
                if (response.body().getCode() == 1){
                    tfTitle.setText(post.getTitle());
                    tfWriter.setText(post.getUser().getUsername());
                    tfContent.setText(post.getContent());
                }
            }

            @Override
            public void onFailure(Call<CMRespDTO<Post>> call, Throwable t) {
                t.printStackTrace();
                MyToast.toast(mContext, t.getMessage());
            }
        });


    }
}