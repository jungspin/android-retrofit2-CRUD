package com.cos.retrofitex03.screens.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.helper.CustomAppBarActivity;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.util.DeleteDialogFragment;
import com.cos.retrofitex03.util.InitSettings;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends CustomAppBarActivity implements InitSettings {

    private static final String TAG = "PostDetailActivity";
    private Context mContext = PostDetailActivity.this;

    private PostController postController = new PostController();

    private TextView tvTitle, tvWriter, tvContent;
    private Button btnUpdate, btnDelete;

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        settingToolBar("detail",true);
        init();
        initLr();
        initSetting();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
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

        Post post = (Post) getIntent().getSerializableExtra("post");
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String authorization = pref.getString("token", "");

        btnUpdate.setOnClickListener(v->{

            Log.d(TAG, "initLr: 클릭");
            Intent intent = new Intent(mContext, PostUpdateActivity.class);
            intent.putExtra("post", post);
            startActivity(intent);
        });
        // 삭제가 안된다..권한이 없댄다..ㅜ -> 서버 다시 키니까 되네...
        btnDelete.setOnClickListener(v->{
            Log.d(TAG, "initLr: btnDelete");
            Call<CMRespDTO> data = postController.deleteById(post.getId(), authorization);
            data.enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
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
        SharedPreferences pref = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String userInfo = pref.getString("principal","");
        Post post = (Post) getIntent().getSerializableExtra("post");

        Gson gson = new Gson();
        User principal = gson.fromJson(userInfo, User.class);

        if (!principal.getUsername().equals(post.getUser().getUsername())){
            btnUpdate.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {
        Post post = (Post) getIntent().getSerializableExtra("post");

        tvTitle.setText(post.getTitle());
        tvWriter.setText(post.getUser().getUsername());
        tvContent.setText(post.getContent());

    }

//    public void showNoticeDialog() {
//        // Create an instance of the dialog fragment and show it
//        DialogFragment dialog = new DeleteDialogFragment();
//        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
//    }

}