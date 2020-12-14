package com.practice.praproject.servcie;

import com.practice.praproject.mapper.RoleMapper;
import com.practice.praproject.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> queryRoleByUserId(String userId) {
        return roleMapper.selectRoleByUserId(userId);
    }

}
