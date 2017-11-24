package com.beryl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.apache.tomcat.jni.User;
import org.apache.tomcat.util.digester.ObjectCreateRule;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qjnup on 2016/11/1.
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public ResponseEntity<Object> handlerFileSizeLimitException(HttpServletRequest request,Throwable ex){

        System.out.println(request.getRequestURL());
        System.out.println(request.getQueryString());
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("msg","超过上传最大文件限制3M");
        return new ResponseEntity<>(map.toString(),responseHeaders, HttpStatus.CREATED);
    }

  /*  @ExceptionHandler(value = Exception.class)
    @ModelAttribute
    public String msg(Model model){
        model.addAttribute("msg","添加额外异常信息");
        return model.toString();
    }*/


}
