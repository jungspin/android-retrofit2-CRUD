package com.cos.retrofitex03.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.screens.post.PostDetailActivity;
import com.cos.retrofitex03.screens.post.PostListActivity;
import com.cos.retrofitex03.util.MySharedPreferences;
import com.cos.retrofitex03.util.MyToast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private static final String TAG = "PostAdapter";

    private PostListActivity mContext;
    private PostController postController = new PostController();
    private List<Post> postList = new ArrayList<>();

    public PostAdapter(PostListActivity mContext){
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // xml 을 메모리에 띄워주는 클래스
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.post_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.setItem(post);
        SharedPreferences pref = mContext.getSharedPreferences("myData", Context.MODE_PRIVATE);
        String authorization = pref.getString("token", "");
        holder.itemView.setOnClickListener(v-> { // holder 가 내가 만든 홀더기때매 itemView 접근 가능
            Log.d(TAG, "onBindViewHolder: 클릭됨 : " + post.getId());

            Call<CMRespDTO> data = postController.findById(post.getId(), authorization);
            data.enqueue(new Callback<CMRespDTO>() {
                @Override
                public void onResponse(Call<CMRespDTO> call, Response<CMRespDTO> response) {
                    if(response.body().getCode() == 1){
                        Gson gson = new Gson();
                        Post postData = gson.fromJson(gson.toJson(response.body().getData()), Post.class);
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                        intent.putExtra("post", postData);
                        mContext.startActivity(intent);
                    }


                }

                @Override
                public void onFailure(Call<CMRespDTO> call, Throwable t) {
                    MyToast.toast(mContext, "잘못된 접근");
                }
            });

        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addItems(List<Post> postList){
        this.postList = postList;
        notifyDataSetChanged();
        // 화면에 그림이 다 그려진 후 데이터가 변경되면 이 함수 없음 화면 다시 안그려짐
        // 최초에는 필요없음
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvWriter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvWriter = itemView.findViewById(R.id.tvWriter);
        }

        public void setItem(Post post){
            tvTitle.setText(post.getTitle());
            tvWriter.setText(post.getUser().getUsername());

        }
    }

}
