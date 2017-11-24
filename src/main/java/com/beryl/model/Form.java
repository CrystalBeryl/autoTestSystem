package com.beryl.model;

import org.springframework.data.annotation.Id;

import javax.annotation.Generated;
import java.util.Date;

/**
 * Created by qjnup on 2016/10/29.
 */
public class Form {

    private int id;
    private int projectId;
    private String title;
    private String description;
    private String attachment;
    private String creator;
    private String assigner;
    private int priority;
    private int status;
    private Date createTime;
    private Date fixedTime;
    private Date refixTime;
    private Date regressTime;
    private Date closeTime;

    private Project project=new Project();


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(Date fixedTime) {
        this.fixedTime = fixedTime;
    }

    public Date getRefixTime() {
        return refixTime;
    }

    public void setRefixTime(Date refixTime) {
        this.refixTime = refixTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getRegressTime() {
        return regressTime;
    }

    public void setRegressTime(Date regressTime) {
        this.regressTime = regressTime;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
