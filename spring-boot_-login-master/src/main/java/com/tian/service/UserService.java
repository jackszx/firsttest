package com.tian.service;

import com.tian.mapper.UserMapper;
import com.tian.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //添加用户
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    //查找用户名
    public User findUserName(String userName) {
        return userMapper.findUserName(userName);
    }

    //查找账号密码
    public String findPassword(String username) {
        return userMapper.findPassword(username);
    }
}
