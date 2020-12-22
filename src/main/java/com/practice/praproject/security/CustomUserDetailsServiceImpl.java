package com.practice.praproject.security;


import com.practice.praproject.pojo.Role;
import com.practice.praproject.pojo.User;
import com.practice.praproject.servcie.RoleService;
import com.practice.praproject.servcie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 登陆身份认证
 * UserDetailsService
 * UserDetailsService接口用于返回用户相关数据。
 * 它有loadUserByUsername()方法，根据username查询用户实体，可以实现该接口覆盖该方法，实现自定义获取用户过程。
 * 该接口实现类被DaoAuthenticationProvider 类使用，用于认证过程中载入用户信息。
 */
@Component(value = "CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.queryUser(name);
        UserDetail userDetail = new UserDetail(user.getId(), user.getUsername(), user.getPassword());
        Role role1 = new Role();
        role1.setCode("ROLE_ADMIN");
        userDetail.getRole().add(role1);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        List<Role> role = roleService.queryRoleByUserId(userDetail.getId());
        userDetail.setRole(role);
        return userDetail;
    }
}
