package com.beryl.mapper;

import com.beryl.model.Form;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/10/29.
 */
public interface FormMapper {

  //创建bug单
   void createBugForm(Form form);
  /*  void createBugForm( @Param("projectId") int projectId,@Param("title") String title,
                        @Param("description") String description, @Param("attachment") String attachment,
                        @Param("creator") String creator, @Param("assigner") String assigner,
                        @Param("priority") int priority, @Param("createTime") Date createTime);*/
  //编辑bug单
  void updateBugForm(@Param("projectId")int projectId,@Param("title")String title,
                     @Param("description")String description,
                     @Param("assigner")String assigner, @Param("priority")int priority,
                     @Param("id")int id);
  //修复bug单
  void fixedBugForm(@Param("fixedTime") Date fixedTime, @Param("id") int id);

  //重新修复bug单
  void refixBugForm(@Param("refixTime") Date refixTime, @Param("id") int id);
  //回归bug单
  void regressBugForm(@Param("regressTime") Date regressTime, @Param("id") int id);

  //关闭bug单
  void closeBugForm(@Param("closeTime") Date closeTime, @Param("id") int id);

  //根据id查询bug单
  Map<String,Object> selectBugForm(int id);

  //查询附件
  String selectAttachment(int id);

  void updateAttachment(@Param("attachment")String attachment,@Param("id")int id);

  //查询表单数目
  int formAmount();
  //分页查询表单
  List<Form> queryFormList(int pageStart);

  //查看分配给自己的任务
  List<Map> myFormTask(@Param("assigner")String assigner, @Param("pageStart")int pageStart);

  int myFormTaskAmount(String assigner);

  //查看自己创建的bug单
  List<Map> myCreateBug(String creator);

  //根据表单id查询表单的创建者
  String queryCreatorById(int id);

}
