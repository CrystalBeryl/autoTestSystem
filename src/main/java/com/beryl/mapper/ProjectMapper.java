package com.beryl.mapper;

import com.beryl.model.Project;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by qjnup on 2016/11/26.
 */
public interface ProjectMapper {

    //创建项目
    void createProject(Project project);
/*    void createProject(@Param("name")String name, @Param("alias")String alias,
                       @Param("comment")String comment, @Param("createTime")Date createTime);*/

    //查询项目alias
    List<String> queryProject();

    //查询项目id
    int queryId(String alias);

    //更新项目信息
    void updateProject(@Param("name")String name, @Param("alias")String alias,
                       @Param("comment")String comment, @Param("id")int id);

    Project queryProjectById(int id);

    //查询项目列表
    List<Project> queryProjectList(int pageStart);

    //查询项目个数
    int getProjectAmount();
}
