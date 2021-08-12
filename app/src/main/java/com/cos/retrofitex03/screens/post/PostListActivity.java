package com.cos.retrofitex03.screens.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.adapter.PostAdapter;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.controller.UserController;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends CustomAppBarActivity implements InitSettings {

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
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String authorization = pref.getString("token", "");
        //Log.d(TAG, "initData: authorization : " + authorization);
        Call<CMRespDTO> postList = postController.findAll(authorization);
        postList.enqueue(new Callback<CMRespDTO>() {
            @Override
            public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                // 선생님은 함수로 만드심!!!!
                if (response.body().getCode() == 1){
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<List<Post>>(){}.getType();
                    List<Post> posts = gson.fromJson(gson.toJson(response.body().getData()), collectionType);
                    // 걍 바디로 바로 받아라 gson 타입 컬렉션
                    //Log.d(TAG, "onResponse: post : " + posts.get(0).getTitle());

                    postAdapter.addItems(posts);
                } else {
                    Log.d(TAG, "onResponse: 정상적인 접근 아님 : " + response.body().getCode());
                    MyToast.toast(mContext, response.body().getMsg());

                }

            }

            @Override
            public void onFailure(Call<CMRespDTO> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);

            }
        });







    }


}