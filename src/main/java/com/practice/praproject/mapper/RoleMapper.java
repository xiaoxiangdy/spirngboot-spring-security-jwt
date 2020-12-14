package com.practice.praproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.praproject.pojo.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {


    @Select("select r.* from user u inner join user_role_rel urr " +
            "on u.id = urr.user_id inner join role r " +
            "on r.id = urr.role_id " +
            "where urr.user_id = #{userId} ")
    List<Role> selectRoleByUserId(String userId);

}
