package com.likeya.job.elastic.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.likeya.job.elastic.config.MySimpleJob;
import com.likeya.job.elastic.domain.Job;
import com.likeya.job.elastic.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobRegistry;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobScheduleController;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.api.JobOperateAPI;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @Description: Job service for managing Elastic Jobs
 * @Author: like
 * @Date: 2024/8/30  11:16
 */
@Slf4j
@Service
public class JobService {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private JobOperateAPI jobOperateAPI;

    @Autowired
    private DataSource dataSource;


    @Transactional
    @DS("moss_tools")
    public void addJob(Job job) {
        try {
            ScheduleJobBootstrap jobBootstrap = createJobBootstrap(job);
            jobBootstrap.schedule();
            log.info("Job {} added successfully", job.getJobName());
        } catch (Exception e) {
            log.error("Failed to add job {}", job.getJobName(), e);
            throw new RuntimeException("Failed to add job", e);
        }
    }

    public void enable(String jobName) {
        jobOperateAPI.enable(jobName, null);
        log.info("Job {} enabled", jobName);
    }

    public void disable(String jobName) {
        jobOperateAPI.disable(jobName, null);
        log.info("Job {} disabled", jobName);
    }

    public void shutdown(String jobName) {
        jobOperateAPI.shutdown(jobName, null);
        log.info("Job {} shut down", jobName);
    }

    public void trigger(String jobName) {
        jobOperateAPI.trigger(jobName);
        log.info("Job {} triggered", jobName);
    }

    public void remove(String jobName) {
        JobScheduleController jobScheduleController = JobRegistry.getInstance().getJobScheduleController(jobName);
        if (Objects.nonNull(jobScheduleController)) {
            jobScheduleController.shutdown();
            zookeeperRegistryCenter.remove("/" + jobName);
            log.info("Job {} removed", jobName);
        } else {
            log.warn("Job {} not found", jobName);
        }
    }

    private ScheduleJobBootstrap createJobBootstrap(Job job) throws ClassNotFoundException {
        MySimpleJob simpleJob = (MySimpleJob) SpringContextHolder.getBean(Class.forName(job.getJobClass()));
        JobConfiguration jobConfig = createJobConfiguration(job.getJobName(), job.getCron(), job.getShardingTotalCount(), job.getJobParameter(), job.getShardingItemParameters());
        return new ScheduleJobBootstrap(zookeeperRegistryCenter, simpleJob, jobConfig);
    }

    private JobConfiguration createJobConfiguration(String jobName, String cron, int shardingTotalCount, String jobParameter, String shardingItemParameters) {
        DataSource ds = ((DynamicRoutingDataSource) dataSource).getDataSource("moss_tools");
        TracingConfiguration<DataSource> tracingConfiguration = new TracingConfiguration<>("RDB", ds);
        return JobConfiguration.newBuilder(jobName, shardingTotalCount)
                .addExtraConfigurations(tracingConfiguration)
//                .jobListenerTypes(MossElasticJobListener.MOSS_ELASTIC_JOB_LISTENER)
                .cron(cron)
                .jobParameter(jobParameter)
                .shardingItemParameters(shardingItemParameters)
                .overwrite(true)
                .build();
    }
}
