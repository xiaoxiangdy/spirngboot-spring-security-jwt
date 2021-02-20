package com.practice.praproject.security;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.practice.praproject.jwt.JWTUtils;
import com.practice.praproject.pojo.Role;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 验证请求token拦截器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeaderName;
    @Autowired
    private JWTUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authToken = httpServletRequest.getHeader(this.tokenHeaderName);//从请求头中获取token
        if (StringUtils.isBlank(authToken)){
            logger.info("访问失败，token为空");
        }
        String username = null;
        String userId = null;
        List<Role> roles = null;
        try {
            Claims claims = jwtUtils.parseJWT(authToken);//解析token，从token中拿到用户相关信息
            if (claims != null) {
                username = (String) claims.get("username");
                userId = (String) claims.get("id");
                roles = JSONArray.parseArray((String) claims.get("roles"),Role.class);
            }
            if (StringUtils.isNotBlank(username)){
                logger.info(String.format("正在检查用户的身份验证 %s",username));
            }
        } catch (Exception e) {
            logger.error(String.format("解析JWT出错 %s",authToken));
            e.printStackTrace();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetail userDetail = new UserDetail(userId, username, "");
            userDetail.setRole(roles);
            if (!jwtUtils.isExpiration(authToken)){
                //如果获取到用户，且用户不为空 保存用户对象到SecurityContextHolder
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                logger.info(String.format("Authenticated userDetail %s, setting security context", username));
                //SecurityContextHolder作用：保留系统当前的安全上下文细节，其中就包括当前使用系统的用户的信息。
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
