package com.cos.retrofitex03.controller;

import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.service.PostService;
import com.cos.retrofitex03.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public class PostController {

    private PostService postService = PostService.service;

    public Call<CMRespDTO<List<Post>>> findAll(String authorization){
        return postService.findAll(authorization);
    }

    public Call<CMRespDTO<Post>> findById(int id, String authorization){
        return postService.findById(id, authorization);
    }

    public Call<CMRespDTO<Post>> updateById(int id, String authorization, Post post){
        return postService.updateById(id, authorization, post);
    }

    public Call<CMRespDTO> deleteById(int id, String authorization){
        return postService.deleteById(id, authorization);
    }

    public Call<CMRespDTO<Post>> insert(String authorization, Post post){
        return postService.insert(authorization, post);
    }


}
