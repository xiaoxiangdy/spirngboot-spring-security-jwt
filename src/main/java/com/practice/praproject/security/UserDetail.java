package com.practice.praproject.security;

import com.practice.praproject.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * createAt: 2018/9/14
 */
public class UserDetail implements UserDetails {
    private String id;
    private String username;
    private String password;
    private List<Role> role = new ArrayList<>();
    private Date lastPasswordResetDate;

    public UserDetail(
            String id,
            String username,
            List<Role> role,
//            Date lastPasswordResetDate,
            String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
//        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public UserDetail(String username, String password, List<Role> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDetail(String username,String password){
        this.username = username;
        this.password = password;
    }

    public UserDetail(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.role.forEach(role1 -> {
            authorities.add(new SimpleGrantedAuthority(role1.getCode()));
        });
        return authorities;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *  账户是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** 账户是否激活
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}
