package com.ssafy.home.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.mapper.UserMapper;
import com.ssafy.home.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String regist(User user) {
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	System.out.println(encodedPassword);
    	user.setPassword(encodedPassword);
    	userMapper.regist(user);
        return jwtUtil.createAccessToken(user.getId());
    }

    @Override
    public User login(User user) {
		User userInfo = userMapper.login(user);	//DB로부터 조회한 정보
		System.out.println(userInfo+" userInfo");
		if(userInfo==null || !passwordEncoder.matches(user.getPassword(), userInfo.getPassword()) ) return null;
		
		return userInfo;
    }

    @Override
    public int findUser(User user) {
        return userMapper.findUser(user);
    }

    @Override
    public User findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }
    
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}
	
	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}
	
	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}
    
}
