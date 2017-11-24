package com.beryl.service;

import com.beryl.mapper.RoleMapper;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/12/24.
 */
@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<String> roleNameList(){
        return roleMapper.roleNameList();
    }


    public int selectRoleId(String roleName){
        return roleMapper.selectRoleId(roleName);
    }

    public List<Map> roleUserList(){
        return roleMapper.roleUserList();
    }

    public List<Map> rolePage(int pageStart){
        return roleMapper.rolePage(pageStart);
    }

}
