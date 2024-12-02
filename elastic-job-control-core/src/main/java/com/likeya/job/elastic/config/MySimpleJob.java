package com.likeya.job.elastic.config;

import cn.hutool.core.util.IdUtil;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

/**
 * @description: 为了实现一些通用的功能，比如日志追踪，封装了该类，所有的job都继承该类
 * @author: like
 * @date: 2024-12-02 18:17
 */
public abstract class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        ThreadContext.put("jobId", IdUtil.fastSimpleUUID());
        doExecute(shardingContext);
    }
    protected abstract void doExecute(ShardingContext shardingContext);

}
