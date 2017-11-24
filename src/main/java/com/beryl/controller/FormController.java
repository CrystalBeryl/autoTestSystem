package com.beryl.controller;

import com.alibaba.fastjson.JSON;
import com.beryl.model.Form;
import com.beryl.service.FormService;
import com.beryl.service.ProjectService;
import com.beryl.service.UserService;
import com.beryl.util.Consts;

import java.util.*;

import com.beryl.util.RoleConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by qjnup on 2016/10/29.
 */
@RestController
@RequestMapping("/autoTest/form")
public class FormController {
    @Autowired
    private FormService formService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
//创建bug单视图
    @RequestMapping("/bugCreate")
    public ModelAndView bugCreate(HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        ModelAndView view = null;
        if(roleId==RoleConsts.TESTER){
            view = new ModelAndView("bugCreate");
        }else {
            view = new ModelAndView("redirect:/autoTest/index");
        }
        return view;
    }

//创建bug单接口
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String  createBugForm(@RequestBody Map param, HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String,Object> map = new HashMap() ;
        Map<String,Object> dataMap = new HashMap() ;
        if(roleId==RoleConsts.TESTER){
            Form form = new Form();
            form.setTitle((String) param.get("title"));
            form.setDescription((String) param.get("description"));
            String prio = (String) param.get("priority");
            int priority = 0;
            if(prio.equals("一般")){
                priority = 0;
            }else if(prio.equals("轻微")){
                priority = 1;
            }else {
                priority = 2;
            }
            form.setPriority(priority);
            form.setAssigner((String) param.get("assigner"));
            String creator = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
            form.setCreator(creator);
            Date createTime = new Date();
            form.setCreateTime(createTime);
            String alias = (String)param.get("alias");
            int projectId = projectService.queryId(alias);
            form.setProjectId(projectId);

            String attachment = (String) param.get("filePath");
            form.setAttachment(attachment);

            int flag = formService.createBugForm(form);
            if (flag == Consts.FORM_TITLE_ISNULL){
                dataMap.put("msg","表单名不能为空");
                map.put("success",false);
                map.put("data",dataMap);
            }else if (flag == Consts.FORM_ASSIGNER_ISNULL){
                dataMap.put("msg","表单处理人不能为空");
                map.put("success",false);
                map.put("data",dataMap);
            }else if (flag == Consts.FORM_CREATOR_ISNULL){
                dataMap.put("msg","表单创建人不能为空");
                map.put("success",false);
                map.put("data",dataMap);
            }else if (flag == Consts.FORM_CREATE_SUCCESS){
                dataMap.put("id",form.getId());
                dataMap.put("msg","表单创建成功");
                map.put("success",true);
                map.put("data",dataMap);
            }else {
                dataMap.put("msg","表单创建失败");
                map.put("success",false);
                map.put("data",dataMap);
            }
        }else {
            dataMap.put("msg","你没有权限创建表单");
            map.put("success",false);
            map.put("data",dataMap);
        }

        return JSON.toJSONString(map);
    }
//编辑bug单视图
    @RequestMapping("/bugEdit/{id}")
    public ModelAndView bugEdit(@PathVariable("id") String id){
        return new ModelAndView("bugEdit");
    }

//查询bug单接口
    @RequestMapping("/bugEditJson/{id}")
    public Object bugEditJson(@PathVariable("id") String id,HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        int formId = Integer.parseInt(id);
        Map<String, Object> formMap = formService.selectBugForm(formId);
        dataMap.put("form",formMap);
        dataMap.put("msg","查询bug单成功");
        dataMap.put("roleId",roleId);
        map.put("success",true);
        map.put("data",dataMap);
        return map;
    }

//编辑bug单接口
    @RequestMapping(value = "/updateBugForm", method = RequestMethod.POST)
    public Object updateBugForm(@RequestBody Map param,HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        if(roleId== RoleConsts.TESTER){
            String alias = (String)param.get("alias");
            int projectId = projectService.queryId(alias);
            String title = (String)param.get("title");
            String description = (String)param.get("description");
            String assigner = (String)param.get("assigner");
            //     String attachment = (String)param.get("attachment");
            String prio = (String) param.get("priority");
            int id = Integer.parseInt((String) param.get("id"));
            int priority = 0;
            if(prio.equals("一般")){
                priority = 0;
            }else if(prio.equals("轻微")){
                priority = 1;
            }else {
                priority = 2;
            }

            Form form = new Form();
            form.setProjectId(projectId);
            form.setTitle(title);
            form.setDescription(description);
            //       form.setAttachment(attachment);
            form.setAssigner(assigner);
            form.setPriority(priority);
            formService.updateBugForm(form,id,projectId);

            dataMap.put("msg","更新bug单成功");
            map.put("success",true);
            map.put("data",dataMap);
        }else {
            dataMap.put("msg","你没有更新bug单的权限");
            map.put("success",false);
            map.put("data",dataMap);
        }

        return map;

    }

    //开发修复bug单
    @RequestMapping(value = "/fixedForm",method = RequestMethod.POST)
    public Object  fixedBugForm(@RequestBody Map param,HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        int formId = Integer.parseInt(param.get("id").toString());
        String creator = formService.queryCreatorById(formId);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        if(roleId==RoleConsts.DEVELOPER){
            Date fixedTime = new Date();
            formService.fixedBugForm(fixedTime,formId);

            dataMap.put("msg","bug修复成功");
            dataMap.put("creator",creator);

            map.put("data",dataMap);
            map.put("success",true);
        }else {
            dataMap.put("msg","你没有修复bug的权限");
            map.put("data",dataMap);
            map.put("success",false);
        }

        return map;
    }

//测试提出重新修复bug
    @RequestMapping(value = "/refixForm",method = RequestMethod.POST)
    public Object refixBugForm(@RequestBody Map param,HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        if(roleId==RoleConsts.TESTER){
            Date refixTime = new Date();
            formService.refixBugForm(refixTime, Integer.parseInt(param.get("id").toString()));
            dataMap.put("msg","bug重新修复");
            dataMap.put("username",username);

            map.put("data",dataMap);
            map.put("success",true);
        }else {
            dataMap.put("msg","你没有权限重新修复bug");
            map.put("data",dataMap);
            map.put("success",false);
        }

        return map;
    }

    @RequestMapping(value = "/regressForm",method = RequestMethod.POST)
    public Object regressBugForm(@RequestBody Map param,HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        if(roleId==RoleConsts.TESTER){
            Date regressTime = new Date();
            formService.regressBugForm(regressTime, Integer.parseInt(param.get("id").toString()));

            dataMap.put("msg","bug回归成功");
            map.put("data",dataMap);
            map.put("success",true);
        }else {
            dataMap.put("msg","你无权回归bug");
            map.put("data",dataMap);
            map.put("success",false);
        }
        return map;

    }

    @RequestMapping(value = "/closeForm",method = RequestMethod.POST)
    public Object closeBugForm(@RequestBody Map param, HttpSession httpSession){
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        if(roleId==RoleConsts.TESTER){
            Date closeTime = new Date();
            formService.closeBugForm(closeTime, Integer.parseInt(param.get("id").toString()));

            dataMap.put("msg","bug关闭成功");
            map.put("data",dataMap);
            map.put("success",true);
        }else{
            dataMap.put("msg","你没有权限关闭bug");
            map.put("data",dataMap);
            map.put("success",false);
        }

        return map;
    }

    @RequestMapping("/formPage/{id}")
    public Object formPage(@PathVariable("id")String id){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        int pageNum = Integer.parseInt(id);
        Map<String,Object> formMap = formService.queryFormList(pageNum);
        dataMap.put("formMap",formMap);
        dataMap.put("msg","bug表单查询成功");
        map.put("data",dataMap);
        map.put("success",true);
        return map;
    }

    @RequestMapping("/bugList")
    public ModelAndView formList(){

        return new ModelAndView("bugList");
    }

    //查看分配到自己的任务
    @RequestMapping("/myFormTask/{id}")
    public Object myFormTask(@PathVariable("id")String id, HttpSession httpSession){
        int pageNum = Integer.parseInt(id);
        String assigner = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        System.out.println("当前的用户是："+assigner);
        Map<String,Object> formMap = formService.myFormTask(assigner,pageNum);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("formMap",formMap);
        dataMap.put("msg","查询bug任务成功");
        dataMap.put("assigner",assigner);
        map.put("data",dataMap);
        map.put("success",true);
        return map;


    }



    }
