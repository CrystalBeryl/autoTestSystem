package com.beryl.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by qjnup on 2016/11/19.
 */

@WebFilter(urlPatterns = "/autoTest/*")
public class SessionFilter implements Filter{

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String sessionId = request.getRequestedSessionId();

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //session.setMaxInactiveInterval(120);
        System.out.println("HttpSession中的sessionId:"+ session.getId());
        System.out.println("过滤器中拦截到的sessionId:"+sessionId);
        if(sessionId==null || username==null){
         //   dispatcher.forward(servletRequest,servletResponse);
            response.sendRedirect("/login");
        }

        else{
                filterChain.doFilter(servletRequest,servletResponse);
                System.out.println("请求："+request.getRequestURL());
            }


    }

    @Override
    public void destroy() {

    }
}
