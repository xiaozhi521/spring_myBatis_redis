package com.cn.service;

import com.cn.bean.Role;

import java.util.List;


public interface RoleService {
	public Role getRole(Long id);

	public int deleteRole(Long id);

	public Role insertRole(Role role);

	public int updateRole(Role role);

	public List<Role> findRoles(String roleName, String note);
	
	public int insertRoles(List<Role> roleList);
}