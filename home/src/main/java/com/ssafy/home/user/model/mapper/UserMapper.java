package com.ssafy.home.user.model.mapper;

import com.ssafy.home.user.dto.User;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    //회원 가입
    int regist(User user);

    //로그인
    User login(User user);

    //중복 조회
    int findUser(User user);

    //내정보 조회
    User findById(String id);

    //내 정보 수정
    int updateUser(User user);

    //회원 탈퇴
    int deleteUser(String id);


	void saveRefreshToken(Map<String, String> map);

	void deleteRefreshToken(Map<String, String> map);

	Object getRefreshToken(String userId);
	

}
