package com.cn.service;

import com.cn.bean.Role;
import java.util.logging.Logger;
import java.util.List;


public interface RoleService {

	public Logger logger = Logger.getLogger(RoleService.class.getName());
	public Role getRole(Long id);

	public int deleteRole(Long id);

	public Role insertRole(Role role);

	public int updateRole(Role role);

	public List<Role> findRoles(String roleName, String note);
	
	public int insertRoles(List<Role> roleList);

	public int insertRoles1(List<Role> roleList);
}