package com.beryl.service;

import com.beryl.mapper.ProjectMapper;
import com.beryl.model.Page;
import com.beryl.model.Project;
import com.beryl.util.Consts;
import com.beryl.util.MybatisHelper;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/11/26.
 */

@Service
@Transactional(readOnly = true)
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional(rollbackFor = Exception.class)
    public void createProject(Project project){
        projectMapper.createProject(project);
        //projectMapper.createProject(name,alias,comment,createTime);
    }

    public List<String> queryProject(){
        return projectMapper.queryProject();
    }

    public int queryId(String alias){
       return projectMapper.queryId(alias);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProject(String name,String alias, String comment,int id){
        projectMapper.updateProject(name,alias,comment,id);
    }

    public Project queryProjectById(int id){
        return projectMapper.queryProjectById(id);
    }

    public Map queryProjectList(int pageNum){
        Page page = new Page();
        page.setAmount(projectMapper.getProjectAmount());
        page.setPageNum(pageNum);
        page.setPageStart();
        page.setTotalPage();
        int totalPage = page.getTotalPage();
        List<Project> projectList = projectMapper.queryProjectList(page.getPageStart());
        Map<String,Object> projectMap = new HashMap();
        projectMap.put("projectList",projectList);
        projectMap.put("totalPage",totalPage);
        return projectMap;
    }

   /* public List projectPage(int pageNum){
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        ProjectMapper projectMapper = sqlSession.getMapper(ProjectMapper.class);
        int pageStart = (pageNum-1)* Consts.pageCount+1;
        PageHelper.startPage(pageStart, Consts.pageCount);
        List<Project> projectList = projectMapper.queryProjectList();
        return projectList;
    }*/


}
