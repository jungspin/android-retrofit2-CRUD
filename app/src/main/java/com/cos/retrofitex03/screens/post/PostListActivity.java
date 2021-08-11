package com.cos.retrofitex03.screens.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.adapter.PostAdapter;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.controller.UserController;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends AppCompatActivity implements InitSettings {

    private static final String TAG = "PostListActivity";
    private Context mContext = PostListActivity.this;
    private PostController postController = new PostController();

    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager layoutManager; // 리니어 레이아웃은 방향이 있음. 방향 설정을 위해
    private PostAdapter postAdapter; // 얘는 내가 띄워야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);



        init();
        initLr();
        initAdapter();
        initData();
    }

    @Override
    public void init() {
        rvPostList = findViewById(R.id.rvPostList);
    }

    @Override
    public void initLr() {


    }

    @Override
    public void initSetting() {

    }

    @Override
    public void initAdapter() {
        rvPostList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        postAdapter = new PostAdapter();
        rvPostList.setAdapter(postAdapter);
    }

    @Override
    public void initData() {
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String authorization = pref.getString("token", "");
        Log.d(TAG, "initData: authorization : " + authorization);
        Call<CMRespDTO> postList =  postController.findAll(authorization);
        postList.enqueue(new Callback<CMRespDTO>() {
            @Override
            public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                Log.d(TAG, "onResponse: response : " + response.body().getData());
                ArrayList<Post> posts = (ArrayList)response.body().getData();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm").create();
                String json = gson.toJson(posts.get(0));
                Log.d(TAG, "onResponse: post : " + json);
//                Post post = gson.fromJson(json, Post.class);
//                Log.d(TAG, "onResponse: post : " + post);

            }

            @Override
            public void onFailure(Call<CMRespDTO> call, Throwable t) {
                Log.d(TAG, "onFailure: onFailure : "  + t);
            }
        });


    }


}