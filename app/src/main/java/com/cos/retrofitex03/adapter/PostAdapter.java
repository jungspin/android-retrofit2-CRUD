package com.cos.retrofitex03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.retrofitex03.R;
import com.cos.retrofitex03.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private static final String TAG = "PostAdapter";

    private List<Post> postList = new ArrayList<>();


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
        private TextView tvTitle, tvTime, tvWriter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvWriter = itemView.findViewById(R.id.tvWriter);
        }

        public void setItem(Post post){
            tvTitle.setText(post.getTitle());
            tvTime.setText(post.getUpdated().toString());
            tvWriter.setText(post.getUser().getUsername());

        }
    }

}
