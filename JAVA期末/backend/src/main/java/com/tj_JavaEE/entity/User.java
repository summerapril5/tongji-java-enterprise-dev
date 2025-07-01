package com.tj_JavaEE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer userId;
    private String nickname;
    private String avatarUrl;
    private String account;
    private String password;
    private String email;
    private String status;
}
