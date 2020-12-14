package com.practice.praproject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.praproject.mapper.UserMapper;
import com.practice.praproject.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PraProjectApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void saveUser(){
        User user = new User();
        user.setPassword("111");
        user.setUsername("222");
        userMapper.insert(user);
    }

    @Test
    public void query(){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        List<User> username = userMapper.selectList(queryWrapper.eq("username", 222).eq("password",""));
        System.out.println(username.toString());
    }

    @Test
    public void testUUID(){

    }






}
