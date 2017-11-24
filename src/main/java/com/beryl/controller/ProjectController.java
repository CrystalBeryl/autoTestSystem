package com.beryl.controller;

import com.beryl.mapper.ProjectMapper;
import com.beryl.model.Project;
import com.beryl.service.ProjectService;
import com.beryl.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/11/26.
 */

@RestController
@RequestMapping("/autoTest/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectMapper projectMapper;

    @RequestMapping(value ="/createProject", method = RequestMethod.POST)
    public Object createProject(@RequestBody Map param){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String name = (String) param.get("name");
        String alias = (String) param.get("alias");
        String comment = (String) param.get("comment");

        if(name==null){
            dataMap.put("msg", Consts.PROJECT_NAME_NULL);
            map.put("data",dataMap);
            map.put("success",false);
            return map;
        }
        if(alias==null){
            dataMap.put("msg", Consts.PROJECT_ALIAS_NULL);
            map.put("data",dataMap);
            map.put("success",false);
            return map;
        }
        Date createTime = new Date();
        Project project = new Project();
        project.setName(name);
        project.setAlias(alias);
        project.setComment(comment);
        project.setCreateTime(createTime);

        try{
            projectService.createProject(project);
            dataMap.put("msg","项目创建成功");
            dataMap.put("id",project.getId());
            map.put("data",dataMap);
            map.put("success",true);

        }catch (Exception e){
            dataMap.put("msg","项目创建失败");
            map.put("data",dataMap);
            map.put("success",false);

        }

        return map;

    }

    @RequestMapping("/queryProject")
    public Object queryProject(){

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        List<String> alias = projectService.queryProject();
        dataMap.put("aliasList",alias);
        dataMap.put("msg","查询项目别名成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

    @RequestMapping("/createProject")
    public ModelAndView createProject(){

        return new ModelAndView("projectCreate");

    }
    @RequestMapping("/editProject/{projectId}")
    public ModelAndView editProject(@PathVariable("projectId")String projectId){

        return new ModelAndView("projectEdit");

    }


    @RequestMapping("/queryProject/{projectId}")
    public Object queryProjectById(@PathVariable("projectId")String projectId){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> projectMap = new HashMap<>();

        int id = Integer.parseInt(projectId);
        Project project = projectService.queryProjectById(id);
        projectMap.put("name",project.getName());
        projectMap.put("alias",project.getAlias());
        projectMap.put("comment",project.getComment());
        dataMap.put("project",projectMap);
        dataMap.put("msg","查询项目成功");
        map.put("data",dataMap);
        map.put("success",true);
        return map;
    }


    @RequestMapping(value = "/updateProject/{projectId}",method = RequestMethod.POST)
    public Object updateProject(@RequestBody Map param, @PathVariable("projectId")String projectId){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String name = (String) param.get("name");
        String alias = (String) param.get("alias");
        String comment = (String) param.get("comment");
        int id = Integer.parseInt(projectId);

        if(name==null){
            dataMap.put("msg", Consts.PROJECT_NAME_NULL);
            map.put("data",dataMap);
            map.put("success",false);
            return map;
        }
        if(alias==null){
            dataMap.put("msg", Consts.PROJECT_ALIAS_NULL);
            map.put("data",dataMap);
            map.put("success",false);
            return map;
        }

        projectService.updateProject(name,alias,comment,id);
        dataMap.put("msg","更新项目成功");
        map.put("success",true);
        map.put("data",dataMap);
        return map;
    }


    @RequestMapping("/projectList")
    public ModelAndView projectList(){
        return new ModelAndView("projectList");
    }

 /*   //查询项目列表
    @RequestMapping("/queryProjectList")
    public Object queryProjectList(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<Project> projectList = projectService.queryProjectList();
        dataMap.put("projectList",projectList);
        dataMap.put("msg","查询项目列表成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }*/

 //分页查询项目列表
    @RequestMapping("/projectPage/{id}")
    public Object projectPage(@PathVariable("id") String id){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        int pageNum = Integer.parseInt(id);
        Map<String, Object> projectMap= projectService.queryProjectList(pageNum);

        dataMap.put("projectMap",projectMap);
        dataMap.put("msg","查询项目列表成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;

    }


}
