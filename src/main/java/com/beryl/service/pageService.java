package com.beryl.service;

import com.beryl.mapper.ProjectMapper;
import com.beryl.model.Project;
import com.beryl.util.Consts;
import com.beryl.util.MybatisHelper;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by qjnup on 2016/12/7.
 */

public class pageService {

/*    public List projectPage(int pageNum){
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        ProjectMapper projectMapper = sqlSession.getMapper(ProjectMapper.class);
        int pageStart = (pageNum-1)*Consts.pageCount+1;
        PageHelper.startPage(pageStart, Consts.pageCount);
        List<Project> projectList = projectMapper.queryProjectList();
        return projectList;
    }*/



}
