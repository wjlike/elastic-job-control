package com.likeya.job.elastic.config;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.likeya.job.elastic.domain.Job;
import com.likeya.job.elastic.domain.SysJob;
import com.likeya.job.elastic.mapper.SysJobMapper;
import com.likeya.job.elastic.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JobInitializer implements SmartLifecycle {

    @Autowired
    private JobService jobService;

    @Autowired
    private Environment env;

    @Autowired
    private SysJobMapper sysJobMapper;

    private boolean running = false;

    @Override
    public void start() {
        try {
            String artifactId = getArtifactId();
            List<SysJob> jobs = getJobsFromDatabase(artifactId);
            for (SysJob job : jobs) {
                jobService.addJob(BeanUtil.copyProperties(job, Job.class));
            }
            running = true;
        } catch (Exception e) {
            log.error("Failed to initialize jobs", e);
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE; // Ensure this starts last
    }

    private String getArtifactId() {
        return env.getProperty("spring.application.name");
    }

    private List<SysJob> getJobsFromDatabase(String artifactId) {
        return sysJobMapper.selectList(Wrappers.lambdaQuery(SysJob.class).eq(SysJob::getArtifactId, artifactId).eq(SysJob::getJobState, "ENABLED"));
    }
}
