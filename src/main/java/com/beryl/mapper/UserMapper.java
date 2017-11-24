package com.beryl.mapper;

import com.beryl.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/10/10.
 */
@Component
public interface UserMapper {
     User getUser(@Param("username") String username);

     //注册用户
     void regUser(@Param("username") String username , @Param("password") String password ,
                     @Param("createTime") Date createTime);

     int checkUserExist(@Param("username") String username);
     //获取全部用户
     ArrayList<String> getAllUsers();

     //查询用户的roleId
     int queryRoleId(@Param("username")String username);

     void updateUserRole(@Param("roleId") int roleId,@Param("username")String username);

     int userCounts();

     List<String> developerNames();
}
