package com.cos.retrofitex03.controller;

import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.service.PostService;
import com.cos.retrofitex03.service.UserService;

import java.util.List;

import retrofit2.Call;

public class PostController {

    private PostService postService = PostService.service;

    public Call<CMRespDTO> findAll(String authorization){
        return postService.findAll(authorization);
    }


}
