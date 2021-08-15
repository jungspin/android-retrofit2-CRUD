package com.cos.retrofitex03.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.cos.retrofitex03.bean.SessionUser;
import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.LoginDTO;
import com.cos.retrofitex03.model.Post;
import com.cos.retrofitex03.model.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// 인증이 필요없는 주소 /auth
// 인증이 필요한 주소 /user, /post

public interface PostService {

    @GET("/post")
    Call<CMRespDTO<List<Post>>> findAll(@Header("Authorization")String authorization);

    @GET("/post/{id}")
    Call<CMRespDTO<Post>> findById(@Path("id")int postId, @Header("Authorization")String authorization);

    @DELETE("/post/{id}")
    Call<CMRespDTO> deleteById(@Path("id")int postId, @Header("Authorization")String authorization);

    @PUT("/post/{id}")
    Call<CMRespDTO<Post>> updateById(@Path("id")int postId, @Header("Authorization")String authorization
                                    ,@Body Post post);

    @POST("/post")
    Call<CMRespDTO<Post>> insert(@Header("Authorization")String authorization,@Body Post post);


    Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://172.30.1.8:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    PostService service = retrofit.create(PostService.class);

//    Interceptor interceptor = new Interceptor() {
//        @NotNull
//        @Override
//        public Response intercept(@NotNull Chain chain) throws IOException {
//            String token = SessionUser.token;
//            Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
//            return chain.proceed(request);
//        }
//    };

}
