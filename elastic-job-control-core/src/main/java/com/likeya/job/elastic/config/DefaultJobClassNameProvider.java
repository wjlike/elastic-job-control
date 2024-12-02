package com.likeya.job.elastic.config;

import com.likeya.job.elastic.utils.AopTargetUtils;
import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.lite.internal.setup.JobClassNameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

/**
 * @Description: 自定义作业类名提供者
 *      需要使用这个类兼容获取spring cglib的bean,否则注册到注册中心会报错
 *      使用SPI META-INF.services注册
 * @Author: like
 * @Date: 2024/1/11  9:16
 */
public class DefaultJobClassNameProvider implements JobClassNameProvider {

    Logger logger = LoggerFactory.getLogger(DefaultJobClassNameProvider.class);
    /**
     * 获取作业类名
     * @param elasticJob ElasticJob对象
     * @return 任务类名
     */
    @Override
    public String getJobClassName(ElasticJob elasticJob) {
        logger.info("Registration elasticJob Name:{}",AopUtils.isAopProxy(elasticJob) ? AopTargetUtils.getTarget(elasticJob).getClass().getName() : elasticJob.getClass().getName());
        return AopUtils.isAopProxy(elasticJob) ? AopTargetUtils.getTarget(elasticJob).getClass().getName() : elasticJob.getClass().getName();
    }
}
