package com.practice.praproject.servcie;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.praproject.mapper.UserMapper;
import com.practice.praproject.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void addUser(User user) {
        int insert = userMapper.insert(user);
    }

    public User queryUser(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        List<User> users = userMapper.selectList(queryWrapper.eq("username", username)
                .eq("password", password));
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }
    public User queryUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        List<User> users = userMapper.selectList(queryWrapper.eq("username", username));
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }


}
