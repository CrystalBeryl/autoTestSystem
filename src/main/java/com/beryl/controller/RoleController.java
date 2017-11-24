package com.beryl.controller;

import com.beryl.model.Page;
import com.beryl.service.RoleService;
import com.beryl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/12/24.
 */

@RestController
@RequestMapping("/autoTest/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @RequestMapping("/roleEdit")
    public ModelAndView roleEdit(){
        return new ModelAndView("roleEdit");
    }

    @RequestMapping("/roleList")
    public ModelAndView roleList(){
        return new ModelAndView("roleList");
    }

    @RequestMapping("/roleNameList")
    public Object roleNameList(){
        Map<String,Object> map = new HashMap();
        Map<String,Object> dataMap = new HashMap();

        dataMap.put("roleNameList", roleService.roleNameList());
        dataMap.put("msg","查询角色列表成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

    @RequestMapping(value = "/updateRoleName", method = RequestMethod.POST)
    public Object updateRoleName(@RequestBody Map param)throws Exception{
        Map<String,Object> map = new HashMap();
        Map<String,Object> dataMap = new HashMap();
        String roleName = (String)param.get("roleName");
        String username = (String)param.get("username");
        int roleId = roleService.selectRoleId(roleName);

        userService.updateUserRole(roleId,username);
        dataMap.put("msg","更新用户角色关系成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

    //查看全部的用户以及角色
    @RequestMapping("/roleUserList")
    public Object roleUserList(){
        Map<String,Object> map = new HashMap();
        Map<String,Object> dataMap = new HashMap();

        dataMap.put("msg","查看用户角色成功");
        dataMap.put("roleUserList",roleService.roleUserList());
        map.put("data",dataMap);
        map.put("success",true);
        return map;
    }

    //分页查看全部的用户以及角色
    @RequestMapping("/rolePage/{id}")
    public Object rolePage(@PathVariable("id") String id){
        Map<String,Object> map = new HashMap();
        Map<String,Object> dataMap = new HashMap();
        Page page = new Page();
        int pageNum = Integer.parseInt(id);
        page.setAmount(userService.userCounts());
        page.setPageNum(pageNum);
        page.setPageStart();
        page.setTotalPage();
        int totalPage = page.getTotalPage();

        List<Map> rolePageList = roleService.rolePage(page.getPageStart());

        dataMap.put("rolePageList",rolePageList);
        dataMap.put("totalPage",totalPage);
        dataMap.put("msg","查询成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

}
