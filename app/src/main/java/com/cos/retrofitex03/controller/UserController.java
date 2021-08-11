package com.cos.retrofitex03.controller;

import com.cos.retrofitex03.model.User;
import com.cos.retrofitex03.service.UserService;

import retrofit2.Call;

public class UserController {

    private UserService userService = UserService.service;

    public Call<CMRespDTO> login(LoginDTO loginDTO){
        return userService.findByUsernameAndPassword(loginDTO);
    }

    public Call<CMRespDTO> join(User user){
        return userService.insert(user);
    }
}
