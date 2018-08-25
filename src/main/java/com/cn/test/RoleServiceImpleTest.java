package com.cn.test;

import com.cn.bean.Role;
import com.cn.service.RoleService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RoleServiceImpleTest {

    @Test
    public void getRoleTest(){
        //使用注解Spring IoC容器
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfig.class, RedisConfig.class);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-MapperFactoryBean.xml");
        //获取角色服务类
        RoleService roleService = ctx.getBean(RoleService.class);
        Role role = new Role();
        role.setRoleName("role_name_2");
        role.setNote("role_note_2");
        //插入角色
        roleService.insertRole(role);
        //获取角色
        Role getRole = roleService.getRole(role.getId());
        getRole.setNote("role_note_2_update");
        //更新角色
        roleService.updateRole(getRole);
        //删除角色
        roleService.deleteRole(5L);
    }
}
