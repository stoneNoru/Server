package com.ssafy.home.user.model.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.ssafy.home.exception.UserNotFoundException;
import com.ssafy.home.exception.UserPasswordNotMatchException;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.dto.UserPwDto;

public interface UserService {
    //회원 가입
    String regist(User user);

    //로그인
    User login(User user) throws UserNotFoundException, UserPasswordNotMatchException;

    //중복 조회
    int findUser(User user);

    //내정보 조회
    User findById(String id);

    //내 정보 수정
    int updateUser(User user);

    //회원 탈퇴
    int deleteUser(String id);

	void saveRefreshToken(String id, String refreshToken)  throws Exception;

	void deleteRefreshToken(String userId) throws Exception;

	Object getRefreshToken(String id) throws Exception;

	void findPassword(UserPwDto user) throws NotFoundException;


}
