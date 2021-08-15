package com.cos.retrofitex03.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String created;
    private String updated;


}
