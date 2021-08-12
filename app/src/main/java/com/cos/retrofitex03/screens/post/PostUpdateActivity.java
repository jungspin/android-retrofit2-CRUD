package com.cos.retrofitex03.screens.post;

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

    private PostController postController = new PostController();

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

        settingToolBar("update", true);
        init();
        initLr();
        initSetting();
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
            Post postData = (Post) getIntent().getSerializableExtra("post");
            String title = tfTitle.getText().toString();
            String content = tfContent.getText().toString();

            Post post = Post.builder().title(title).content(content).build();
            SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
            String authorization = pref.getString("token", "");
            Call<CMRespDTO> data = postController.updateById(postData.getId(), authorization, post);
            data.enqueue(new Callback<CMRespDTO>() {
                // 왜 자꾸 권한이 없대..? 내가 안써서 권한 없구나..미쳤다 대박
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                    Log.d(TAG, "onResponse: " +response.body());
                    if (response.body().getCode() == 1){
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Post>(){}.getType();
                        Post post = gson.fromJson(gson.toJson(response.body().getData()), collectionType);
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("post", post);
                        startActivity(intent);
                    } else {
                        MyToast.toast(mContext, response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<CMRespDTO> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });

        });




    }

    @Override
    public void initSetting() {
        Post postData = (Post) getIntent().getSerializableExtra("post");
        tfTitle.setText(postData.getTitle());
        tfWriter.setText(postData.getUser().getUsername());
        tfContent.setText(postData.getContent());

    }

    @Override
    public void initData() {


    }
}