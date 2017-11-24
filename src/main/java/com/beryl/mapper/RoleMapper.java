package com.beryl.mapper;

import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

/**
 * Created by qjnup on 2016/12/24.
 */
public interface RoleMapper {

    List<String> roleNameList();
    int selectRoleId(String roleName);
    List<Map> roleUserList();

    List<Map> rolePage(int pageStart);

}
