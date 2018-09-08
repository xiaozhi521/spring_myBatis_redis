package com.cn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisTemplateServiceImpl implements RedisTemplateService {

    @Autowired
    private RedisTemplate redisTemplate = null;

    /**
     *  使用SessionCallback接口实现多个命令在一个redis连接执行
     */
    @Override
    public void execMultiCommond() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.boundValueOps("key1").set("value1");
                redisOperations.boundHashOps("hash").put("hash-key1","hash-value-1");

                return redisOperations.boundValueOps("key1").get();
            }
        });
        System.out.println(obj);
    }

    /**
     * 使用 SessionCallback 接口实现事务在一个 redis 连接执行
     */
    @Override
    public void execTransaction() {
        List list = (List) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //监控
                redisOperations.watch("key1");
                //打开事务
                redisOperations.multi();
                //命令都不会马上执行，只会放到redis队列中，只会返回null
                redisOperations.boundValueOps("key1").set("mqf");
                redisOperations.boundHashOps("hash").put("hash-key1","hash-value-1");
                redisOperations.opsForValue().get("key1");
                //执行exec方法后会触发事务执行，返回结果，放到list中
                List list = redisOperations.exec();
                return list;
            }
        });
        System.out.println(list);
    }

    /**
     * 执行流水线，将多个命令一次性发给Redis服务器
     */
    @Override
    public void execPipeline() {
        //使用匿名实现类
        List list = redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //在流水线下，命令不会马上返回结果，结果是一次性返回执行的
                redisOperations.opsForValue().set("key1","mqf");
                redisOperations.opsForValue().get("key1");
                return null;
            }
        });
    }
}
