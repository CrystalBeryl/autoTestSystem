package com.beryl.service;

import com.beryl.mapper.FormMapper;
import com.beryl.model.Form;
import com.beryl.model.Page;
import com.beryl.util.Consts;
import com.sun.tools.internal.jxc.ap.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/10/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FormService {

    @Autowired
    private FormMapper formMapper;

    public int createBugForm(Form form){
        String title = form.getTitle();
        String description = form.getDescription();
        String attachment = form.getAttachment();
        String assigner = form.getAssigner();
        int priority = form.getPriority();
        String creator = form.getCreator();
        Date createTime = form.getCreateTime();
        int projectId = form.getProjectId();
        if(title.equals("")||title==null){
            return Consts.FORM_TITLE_ISNULL;
        }
        if(assigner.equals("")||assigner==null){
            return Consts.FORM_ASSIGNER_ISNULL;
        }
        if (creator.equals("")||creator==null){
            return Consts.FORM_CREATOR_ISNULL;
        }

 //       formMapper.createBugForm(projectId,title,description,attachment,creator,assigner,priority,createTime);
        formMapper.createBugForm(form);

        return Consts.FORM_CREATE_SUCCESS;
    }
    public void fixedBugForm(Date createTime, int id){
        formMapper.fixedBugForm(createTime, id);
    }
    public void refixBugForm(Date refixTime, int id){
        formMapper.refixBugForm(refixTime, id);
    }
    public void regressBugForm(Date regressTime, int id){
        formMapper.regressBugForm(regressTime, id);
    }
    public void closeBugForm(Date closeTime, int id){
        formMapper.closeBugForm(closeTime, id);
    }

    public Map selectBugForm(int id){
        Map<String,Object> formMap = formMapper.selectBugForm(id);
       /* Map<String,Object> map = new HashMap();
        map.put("projectAlias",form.getProject().getAlias());
        map.put("title",form.getTitle());
        map.put("description",form.getDescription());
        map.put("attachment",form.getAttachment());*/
        int prio = (int) formMap.get("priority");
/*
        int prio = form.getPriority();
*/
        String priority = null;
        if(prio==0){
            priority = "一般";
        }else if(prio==1){
            priority = "轻微";
        }else {
            priority = "严重";
        }
        formMap.put("priority",priority);
/*
        map.put("assigner",form.getAssigner());
*/
        return formMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateBugForm(Form form,int id,int projectId){

        String title = form.getTitle();
        String description = form.getDescription();
        String attachment = form.getAttachment();
        String assigner = form.getAssigner();
        int priority = form.getPriority();

        String alias = form.getProject().getAlias();
        if(title.equals("") || title==null ){
            return Consts.FORM_TITLE_ISNULL;
        }
        if(assigner.equals("") || assigner==null){
            return Consts.FORM_ASSIGNER_ISNULL;
        }

        formMapper.updateBugForm(projectId,title,description,assigner,priority,id);
        return Consts.FORM_UPDATE_SUCCESS;
    }

    public String selectAttachment(int id){
        return formMapper.selectAttachment(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAttachment(String attachment,int id){
        formMapper.updateAttachment(attachment,id);
    }

    //分页查询Bug表单
    public Map queryFormList(int pageNum){
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setAmount(formMapper.formAmount());
        page.setTotalPage();
        page.setPageStart();
        int totalPage = page.getTotalPage();
        List<Form> formList = formMapper.queryFormList(page.getPageStart());
        Map<String, Object> formMapper = new HashMap<>();
        formMapper.put("formList",formList);
        formMapper.put("totalPage",totalPage);
        return formMapper;
    }

    //查看分配给自己的任务
    public Map<String, Object> myFormTask(String assigner,int pageNum){
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageStart();
        page.setAmount(formMapper.myFormTaskAmount(assigner));
        page.setTotalPage();
        int totalPage = page.getTotalPage();
        Map<String,Object> map = new HashMap<>();
        map.put("formList",formMapper.myFormTask(assigner,page.getPageStart()));
        map.put("totalPage",totalPage);
        return map;
    }


    //查看自己创建的bug单
    public Map<String, Object> myCreateBug(String creator){
        Map<String,Object> map = new HashMap<>();
        map.put("formList", formMapper.myCreateBug(creator));
        return map;
    }

    public String queryCreatorById(int id){
        return formMapper.queryCreatorById(id);
    }

}
