package com.cn.serviceImpl;

import java.util.List;
import java.util.logging.Logger;

import com.cn.bean.Role;
import com.cn.mappings.RoleMapper;
import com.cn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**** imports ****/
@Service
public class RoleServiceImpl implements RoleService {

	public Logger logger = Logger.getLogger(RoleServiceImpl.class.getName());

	@Autowired
	private RoleMapper roleMapper = null;

	/**
	 *  使用 @Cacheable 定义缓存策略，
	 *  当缓存中有值，则返回缓存数据，否则访问方法得到数据
	 *  通过value 引用缓存管理器，通过 key 定义键
	 * @param id 角色编号
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@Cacheable(value = "redisCacheManager", key = "'redis_role_'+#id")
	public Role getRole(Long id) {
		return roleMapper.getRole(id);
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#result.id")
	public Role insertRole(Role role) {
		roleMapper.insertRole(role);
		return role;
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#role.id")
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CacheEvict(value = "redisCacheManager", key = "'redis_role_'+#id")
	public int deleteRole(Long id) {
		return roleMapper.deleteRole(id);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Role> findRoles(String roleName, String note) {
		return roleMapper.findRoles(roleName, note);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)  
	public int insertRoles(List<Role> roleList) {
	    for (Role role : roleList) {
	        this.insertRole(role);
	    }
	    return roleList.size();
	}
}