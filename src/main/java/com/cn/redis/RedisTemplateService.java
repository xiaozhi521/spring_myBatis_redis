package com.cn.redis;

public interface RedisTemplateService {
    /**
     *  执行多个命令
     */
    public void execMultiCommond();

    /**
     *  执行 Redis 事务
     */
    public void execTransaction();

    /**
     *  执行 Redis 流水线
     */
    public void execPipeline();
}
