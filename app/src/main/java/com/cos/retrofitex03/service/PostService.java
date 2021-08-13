package com.cos.retrofitex03.service;

import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.LoginDTO;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostService {

    @GET("post")
    Call<CMRespDTO> findAll(@Header("Authorization")String authorization);

    @GET("post/{id}")
    Call<CMRespDTO> findById(@Path("id")int id, @Header("Authorization")String authorization);

    @DELETE("post/{id}")
    Call<CMRespDTO> deleteById(@Path("id")int id, @Header("Authorization")String authorization);

    @PUT("post/{id}")
    Call<CMRespDTO> updateById(@Path("id")int id, @Header("Authorization")String authorization
                                    ,@Body Post post);

    @POST("post")
    Call<CMRespDTO> insert(@Header("Authorization")String authorization,@Body Post post);


    Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://10.100.202.65:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostService service = retrofit.create(PostService.class);
}
