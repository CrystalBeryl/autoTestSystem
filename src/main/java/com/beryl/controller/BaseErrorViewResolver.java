package com.beryl.controller;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by qjnup on 2016/11/16.
 */
public class BaseErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest httpServletRequest, HttpStatus httpStatus, Map<String, Object> map) {
        if(httpStatus.is4xxClientError()){
            return new ModelAndView("error/404");
        }
        if(httpStatus.is5xxServerError()){
            return new ModelAndView("error/500");
        }
        return new ModelAndView("error/500");
    }


}
