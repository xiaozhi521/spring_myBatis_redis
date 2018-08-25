package com.cn.test;

import com.cn.bean.User;
import com.cn.mappings.UserMapper;
import com.cn.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;


public class UserTest {

    /**
     *  测试redis
     */
    @Test
    public void userService(){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-MapperFactoryBean.xml");
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");

        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        //设值
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForValue().set("key2","value2");
        //通过key取值
        String key1 = (String) redisTemplate.opsForValue().get("key1");
        System.out.println(key1);
        //求长度
        Long length = redisTemplate.opsForValue().size("key2");
        System.out.println("key2长度：" + length);


    }
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
