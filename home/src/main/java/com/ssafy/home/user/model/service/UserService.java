package com.ssafy.home.user.model.service;

import com.ssafy.home.user.dto.User;

public interface UserService {
    //회원 가입
    int regist(User user);

    //로그인
    User login(User user);

    //중복 조회
    int findUser(User user);

    //내정보 조회
    User findById(User user);

    //내 정보 수정
    int updateUser(User user);

    //회원 탈퇴
    int deleteUser(String id);
}
