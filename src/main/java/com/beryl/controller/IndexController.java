package com.beryl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by qjnup on 2016/11/19.
 */

@RestController
@RequestMapping("/autoTest")
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
