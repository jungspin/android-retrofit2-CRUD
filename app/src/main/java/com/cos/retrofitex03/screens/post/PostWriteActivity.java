package com.cos.retrofitex03.screens.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.InitSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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

        settingToolBar("write", true);
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
            SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
            String authorization = pref.getString("token", "");
            Call<CMRespDTO> data = postController.insert(authorization, post);
            data.enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Post>(){}.getType();
                    Post post = gson.fromJson(gson.toJson(response.body().getData()), collectionType);
                    Log.d(TAG, "onResponse: " + post.getTitle());

                    Intent intent = new Intent(mContext, PostDetailActivity.class);
                    intent.putExtra("post", post);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<CMRespDTO> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });


        });

    }

    @Override
    public void initSetting() {
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String userInfo = pref.getString("principal", "");
        Gson gson = new Gson();
        User principal = gson.fromJson(userInfo, User.class);
        tfWriter.setText(principal.getUsername());
    }

    @Override
    public void initData() {

    }
}