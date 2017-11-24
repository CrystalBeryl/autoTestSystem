package com.beryl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by qjnup on 2016/12/18.
 */
@RestController
@RequestMapping("/autoTest/module")
public class ModuleController {

    @RequestMapping("/pageHeader")
    public ModelAndView pageHeader(){
        return new ModelAndView("pageHeader");
    }

    @RequestMapping("/pageFooter")
    public ModelAndView pageRoot(){
        return new ModelAndView("pageFooter");
    }

}
