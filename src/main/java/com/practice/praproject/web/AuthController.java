package com.practice.praproject.web;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.practice.praproject.pojo.ResponseUserToken;
import com.practice.praproject.pojo.ResultCode;
import com.practice.praproject.pojo.ResultJson;
import com.practice.praproject.pojo.User;
import com.practice.praproject.security.UserDetail;
import com.practice.praproject.servcie.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getHello(){
        return "hello World";
    }

    @PostMapping("/login")
    public ResultJson<ResponseUserToken> login(@RequestBody User user){
        ResponseUserToken response = authService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(response);
    }

    @PostMapping(value = "/register")
    public ResultJson sign(@RequestBody User user){
        if (StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getUsername())) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        UserDetail userDetail = new UserDetail(user.getUsername(),user.getPassword());
        return ResultJson.ok(authService.register(userDetail));
    }

}
