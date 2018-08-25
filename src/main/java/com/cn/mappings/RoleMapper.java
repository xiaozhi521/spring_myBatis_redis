package com.cn.mappings;

import com.cn.bean.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    public Role getRole(Long id);

    public int deleteRole(Long id);

    public int insertRole(Role role);

    public int updateRole(Role role);

    public List<Role> findRoles(@Param("roleName") String roleName,@Param("note")String note);
}
