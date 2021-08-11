package com.cos.retrofitex03.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Post {
    private int id;
    private String title;
    private String content;
    private User user;
    private Timestamp created;
    private Timestamp updated;
}
