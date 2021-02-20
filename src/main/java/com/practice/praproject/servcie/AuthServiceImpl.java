package com.practice.praproject.servcie;

import com.practice.praproject.exception.CustomException;
import com.practice.praproject.jwt.JWTUtils;
import com.practice.praproject.pojo.ResponseUserToken;
import com.practice.praproject.pojo.ResultCode;
import com.practice.praproject.pojo.ResultJson;
import com.practice.praproject.pojo.User;
import com.practice.praproject.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {
    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    JWTUtils jwtUtils;

    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //获取当前用户
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = "";
        try {
            //生成token
            token = jwtUtils.createJWT(userDetail.getUsername(),userDetail.getPassword(),userDetail.getRole(),"");

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResultJson.failure(ResultCode.SERVER_ERROR, e.getMessage()));
        }
        //存储token
//        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, userDetail);

    }

    public UserDetail register(UserDetail userDetail){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userDetail.getPassword();
        userDetail.setPassword(encoder.encode(rawPassword));
        userService.addUser(new User(userDetail.getUsername(),userDetail.getPassword()));
        return userDetail;
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage()));
        }
    }

}
