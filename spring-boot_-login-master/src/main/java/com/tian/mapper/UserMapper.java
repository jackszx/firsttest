package com.tian.mapper;

import com.tian.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    //注册用户
    int addUser(User user);

    //验证用户名
    User findUserName(String userName);

    //验证密码
    String findPassword(String username);
}
