package com.practice.praproject.pojo;

import com.practice.praproject.security.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;


public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;

    public ResponseUserToken(String token, UserDetail userDetail) {
        this.token = token;
        this.userDetail = userDetail;
    }

    public ResponseUserToken(){}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
