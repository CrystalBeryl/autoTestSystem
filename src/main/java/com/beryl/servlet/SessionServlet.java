package com.beryl.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by qjnup on 2016/11/19.
 */

//@WebServlet(urlPatterns = "/autoTest/*")
public class SessionServlet{


  //  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        doPost(request,response);
    }

  //  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

     /*   if (request.getRequestedSessionId() == null) {
            response.sendRedirect("/login");
        }else{
            System.out.println(request.getContextPath());
            System.out.println(request.getRequestURL());
            request.getRequestDispatcher(request.getContextPath()+request.getRequestURL()).forward(request,response);

        }*/
    }

}
