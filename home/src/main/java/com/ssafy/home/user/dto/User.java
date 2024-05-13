package com.ssafy.home.user.dto;

import lombok.Data;

@Data
public class User {
    private String id;
    private String password;
    private String email;
    private String nickname;
    private String ROLE;
}
