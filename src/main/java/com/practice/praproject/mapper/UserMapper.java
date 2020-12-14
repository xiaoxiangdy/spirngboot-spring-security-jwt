package com.practice.praproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.praproject.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> getAllUsers();

    @Select({"<script> " +
            "select * from user where 1 = 1" +
            "<if test = 'id != null and id != \"\" '>" +
            "and id = #{id}" +
            "</if>" +
            "<if test = 'username != null and username != \"\" '>" +
            "and username = #{username}" +
            "</if>" +
            "</script>"})
    User getUser(String id, String username);
}
