package com.beryl.service;

import com.beryl.mapper.UserMapper;
import com.beryl.model.User;
import com.beryl.util.Consts;
import com.beryl.util.PwDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by qjnup on 2016/9/29.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    public UserMapper userMapper;

    public int login(String username, String password) {
        User user = null;
        PwDigest pwDigest = new PwDigest();
        password = pwDigest.digest(password);
        //用户名已经存在，判断用户名跟密码是否正确
        if (userMapper.checkUserExist(username) != 0) {
            user = userMapper.getUser(username);
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return Consts.LOGIN_SUCCESS;
            } else {
                return Consts.LOGIN_USER_PW_ERROE;
            }
        }
        else {
            return Consts.LOGIN_USER_NOT_EXISTS;
        }
    }

    //注册用户
@Transactional(rollbackFor = Exception.class)
    public boolean regUser( String username ,String password , Date createTime){
            if(userMapper.checkUserExist(username)!=0){
                return false;
            }
            else {
                userMapper.regUser(username,password,createTime);
                return true;
            }
    }

    //查找全部用户
    public ArrayList<String> getAllUsers(){
        return userMapper.getAllUsers();
    }
    public int queryRoleId(String username){
        return userMapper.queryRoleId(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(int roleId,String roleName){
        userMapper.updateUserRole(roleId,roleName);
    }

    //获取用户数
    public int userCounts(){
       return userMapper.userCounts();
    }

    //获取开发人员list
    public List<String> developerNames(){
        return userMapper.developerNames();
    }

}

