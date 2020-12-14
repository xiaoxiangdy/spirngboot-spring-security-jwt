package com.practice.praproject.pojo;

public class UserRoleRel extends BasicField {

    private String userId;

    private String roleId;

    public  UserRoleRel(){}

    public UserRoleRel(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
