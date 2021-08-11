package com.cos.retrofitex03.controller;

import lombok.Data;

@Data
public class CMRespDTO<T> {
    private int code;
    private String msg;
    private T data;
}
