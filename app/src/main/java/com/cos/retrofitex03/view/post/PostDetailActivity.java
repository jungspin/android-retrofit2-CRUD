package com.cos.retrofitex03.view.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends CustomAppBarActivity implements InitSettings {

    private static final String TAG = "PostDetailActivity";
    private Context mContext = PostDetailActivity.this;

    private PostController postController;

    private TextView tvTitle, tvWriter, tvContent;
    private Button btnUpdate, btnDelete;

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
        initLr();
        initSetting();
        //initData();

    }
    

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        initData();
    }

    @Override
    public void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvWriter = findViewById(R.id.tvWriter);
        tvContent = findViewById(R.id.tvContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    @Override
    public void initLr() {
        btnUpdate.setOnClickListener(v->{
            int postId = getIntent().getIntExtra("postId", 0);

            Intent intent = new Intent(mContext, PostUpdateActivity.class);
            intent.putExtra("postId", postId);
            finish(); // 얘가 없으면 오류나. 뒤로 갔을 때 서버 요청하면서 어떤 무언가가..일어남...그래서 피니쉬필요
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v->{
            Log.d(TAG, "initLr: btnDelete");
            int postId = getIntent().getIntExtra("postId", 0);
            postController.deleteById(postId, SessionUser.token).enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    if (response.body().getCode() == 1){
                        Intent intent = new Intent(mContext, PostListActivity.class);
                        startActivity(intent);
                    } else {
                        MyToast.toast(mContext, response.body().getMsg());
                    }
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
        //Log.d(TAG, "initSetting: writer : " + tvWriter.getText());
        settingToolBar("detail",true);
    }

    @Override
    public void initData() {
        Log.d(TAG, "initData: ");
        int postId = getIntent().getIntExtra("postId",0);
        postController = new PostController();
        postController.findById(postId, SessionUser.token).enqueue(new Callback<CMRespDTO<Post>>() {
            @Override
            public void onResponse(Call<CMRespDTO<Post>> call, Response<CMRespDTO<Post>> response) {
                Post post = response.body().getData();
                //Log.d(TAG, "onResponse: " + cm.getCode());
                //Log.d(TAG, "onResponse: " + cm.getData().getTitle());
                if (response.body().getCode() == 1){
                    tvTitle.setText(post.getTitle());
                    tvWriter.setText(post.getUser().getUsername());
                    tvContent.setText(post.getContent());

                    if (!SessionUser.user.getUsername().equals(post.getUser().getUsername())){
                        btnUpdate.setVisibility(View.INVISIBLE);
                        btnDelete.setVisibility(View.INVISIBLE);
                    } else {
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CMRespDTO<Post>> call, Throwable t) {

            }
        });



    }

//    public void showNoticeDialog() {
//        // Create an instance of the dialog fragment and show it
//        DialogFragment dialog = new DeleteDialogFragment();
//        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
//    }

}