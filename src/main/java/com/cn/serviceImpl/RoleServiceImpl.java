package com.cn.serviceImpl;

import java.util.List;
import java.util.logging.Logger;

import com.cn.bean.Role;
import com.cn.mappings.RoleMapper;
import com.cn.service.RoleService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**** imports ****/
@Service
public class RoleServiceImpl implements RoleService, ApplicationContextAware {

	public Logger logger = Logger.getLogger(RoleServiceImpl.class.getName());

	@Autowired
	private RoleMapper roleMapper = null;

	private ApplicationContext applicationContext = null;

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
		System.out.println("11111111111111");
		return roleMapper.getRole(id);
	}
	/**
	 * 使用@CachePut则表示无论如何都会执行方法，最后将方法的返回值再保存到缓存中
	 * 使用在插入数据的地方，则表示保存到数据库后，会同期插入到Redis缓存中
	 *
	 * @param role
	 *            角色对象
	 * @return 角色对象（会回填主键）
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#result.id")
	public Role insertRole(Role role) {
		roleMapper.insertRole(role);
		return role;
	}

	/**
	 * 使用@CachePut，表示更新数据库数据的同时，也会同步更新缓存
	 *
	 * @param role
	 *            角色对象
	 * @return 影响条数
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#role.id")
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * 使用@CacheEvict删除缓存对应的key
	 *
	 * @param id
	 *            角色编号
	 * @return 返回删除记录数
	 */
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

	/**
	 *  同一类的方法调用自己方法，产生自调用[插入：失效]问题 --- 自调用失效
	 * @param roleList
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)  
	public int insertRoles(List<Role> roleList) {
	    for (Role role : roleList) {
			//同一类的方法调用自己方法，产生自调用[插入：失效]问题
	        this.insertRole(role);
	    }
	    return roleList.size();
	}

	/**
	 *  解决自调用失效问题
	 * @param roleList
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int insertRoles1(List<Role> roleList) {
		for (Role role : roleList) {
			//从容器中获取RoleService对象，实际是一个代理对象
			RoleService roleService= applicationContext.getBean(RoleService.class);
			roleService.insertRole(role);
		}
		return roleList.size();
	}
	//使用生命周期的接口方法，获取IoC容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}