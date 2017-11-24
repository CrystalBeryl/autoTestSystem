package com.beryl.model;


import java.util.Date;
import java.util.regex.Pattern;


/**
 * Created by qjnup on 2016/9/29.
 */


public class User {

    private int id ;
    private int roleId = 0;
    private String username;
    private String password;
    private Date createTime;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
  /*      String str = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
        if(Pattern.matches(str,username)){
            this.username = username;
            return true;
        }
         return false;*/
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public User(){

    }

    public User(int id, int roleId, String username, String password, Date createTime) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

}
