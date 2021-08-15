package com.cos.retrofitex03.view.post;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.bean.SessionUser;
import com.cos.retrofitex03.view.post.adapter.PostAdapter;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends CustomAppBarActivity implements InitSettings {

    private static final String TAG = "PostListActivity";
    private Context mContext = PostListActivity.this;
    private PostController postController;

    private RecyclerView rvPostList;
    private RecyclerView.LayoutManager layoutManager; // 리니어 레이아웃은 방향이 있음. 방향 설정을 위해
    private PostAdapter postAdapter; // 얘는 내가 띄워야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        settingToolBar("list");

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
        postAdapter = new PostAdapter((PostListActivity) mContext);
        rvPostList.setAdapter(postAdapter);
    }

    @Override
    public void initData() {
        postController = new PostController();
        Call<CMRespDTO<List<Post>>> postList = postController.findAll(SessionUser.token);
        postList.enqueue(new Callback<CMRespDTO<List<Post>>>() {
            @Override
            public void onResponse(Call<CMRespDTO<List<Post>>> call, Response<CMRespDTO<List<Post>>> response) {
                if (response.body().getCode() == 1){
                    CMRespDTO<List<Post>> cm = response.body();
                    //Log.d(TAG, "onResponse: " + posts.get(0).getTitle());
                    postAdapter.addItems(cm.getData());
                } else {
                    Log.d(TAG, "onResponse: 정상적인 접근 아님 : " + response.body().getCode());
                    MyToast.toast(mContext, response.body().getMsg());

                }

            }

            @Override
            public void onFailure(Call<CMRespDTO<List<Post>>> call, Throwable t) {
                t.printStackTrace();
                MyToast.toast(mContext, "통신실패");
            }
        });
    }
}