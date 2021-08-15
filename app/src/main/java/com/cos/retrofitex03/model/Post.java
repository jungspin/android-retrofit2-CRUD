package com.cos.retrofitex03.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class Post implements Serializable {
    private int id;
    private String title;
    private String content;
    private User user;
    private String created;
    private String updated;

//    public Timestamp getCreated() {
//        return Timestamp.valueOf(this.created);
//    }
//
//    public Timestamp getUpdated() {
//        return Timestamp.valueOf(this.updated);
//    }
}
