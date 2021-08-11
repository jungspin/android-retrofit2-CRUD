package com.cos.retrofitex03.service;

import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.LoginDTO;
import com.cos.retrofitex03.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostService {

    @GET("post")
    Call<CMRespDTO> findAll(@Header("Authorization")String authorization);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.30.1.41:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostService service = retrofit.create(PostService.class);
}
