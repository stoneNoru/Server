package com.ssafy.home.user.model.service;

import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public int regist(User user) {
        return userMapper.regist(user);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
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
}
