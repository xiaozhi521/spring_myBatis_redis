package com.cn.test;

import com.cn.bean.User;
import com.cn.mappings.UserMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class UserTest {

    //通过`MapperFactoryBean`创建代理对象
    @Test
    public void mapperFactoryBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-MapperFactoryBean.xml");
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        User user = userMapper.getUserById(1);
        System.out.println(user.toString());

    }
    //通过`MapperFactoryBean`创建代理对象
    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        User user = userMapper.getUserById(1);
        System.out.println(user.toString());

    }
}
