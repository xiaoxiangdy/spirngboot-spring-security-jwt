package com.practice.praproject.security;

import com.practice.praproject.pojo.ResultCode;
import com.practice.praproject.pojo.ResultJson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
/**
 * 认证失败处理类，返回401
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * 当用户请求了一个受保护的资源，但是用户没有通过认证，
 * 那么抛出异常，AuthenticationEntryPoint. Commence(..)就会被调用。
 * 这个对应的代码在ExceptionTranslationFilter中，
 * 当ExceptionTranslationFilter catch到异常后，就会间接调用AuthenticationEntryPoint
 * (推测：当 SecurityContextHolder.getContext() 为空值时会触发)
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("认证失败" + e.getMessage());
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("UTF-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        String body = ResultJson.failure(ResultCode.UNAUTHORIZED, e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();
    }
}
