package com.likeya.job.elastic.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.likeya.job.elastic.domain.SysJob;
import com.likeya.job.elastic.mapper.SysJobMapper;
import com.likeya.job.elastic.service.enums.JobStatusEnum;
import com.likeya.job.elastic.service.service.SysJobService;
import com.likeya.job.elastic.utils.PageVO;
import com.likeya.job.elastic.utils.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author like
 * @Description: 表用于存储关于Elastic Jobs的信息 实现类
 * @date 2024-09-20 10:05:37
 */
@Slf4j
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {


    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public IPage pageList(PageVO<SysJob> pageVo) {
        LambdaQueryWrapper<SysJob> queryWrapper = new LambdaQueryWrapper<>(SysJob.class).setEntity(pageVo.getEntity());
        return baseMapper.selectPage(pageVo, queryWrapper);
    }

    /**
     * 保存表用于存储关于Elastic Jobs的信息
     *
     * @param sysJob
     * @return
     */
    @Override
    public Boolean saveSysJob(SysJob sysJob) {
        if (Objects.isNull(sysJob.getId())) {
            sysJob.setJobState(JobStatusEnum.UNREGISTERED.name());
            return save(sysJob);
        } else {
            return updateById(sysJob);
        }
    }

    /**
     * 更新表用于存储关于Elastic Jobs的信息
     *
     * @param sysJob
     * @return
     */
    @Override
    public Boolean updateSysJob(SysJob sysJob) {
        return updateById(sysJob);
    }

    @Override
    public void enable(String jobName) {

        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            if(JobStatusEnum.DISABLED.name().equals(sysJob.getJobState())){
                this.callHook(sysJob.getArtifactId(), "/elastic-job/enable", Map.of("jobName", jobName));
            }else {
                this.callHook(sysJob.getArtifactId(), "/elastic-job/add", BeanUtil.beanToMap(sysJob));
            }
        });
        updateJobStatus(jobName, JobStatusEnum.ENABLED);
    }

    @Override
    public void disable(String jobName) {
        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            callHook(sysJob.getArtifactId(),"/elastic-job/disable", Map.of("jobName", jobName));
        });
        updateJobStatus(jobName, JobStatusEnum.DISABLED);
    }

    @Override
    public void shutdown(String jobName) {
        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            callHook(sysJob.getArtifactId(),"/elastic-job/shutdown", Map.of("jobName", jobName));
        });
        updateJobStatus(jobName, JobStatusEnum.CLOSED);
    }

    @Override
    public void remove(String jobName) {
        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            callHook(sysJob.getArtifactId(),"/elastic-job/remove", Map.of("jobName", jobName));
        });
        lambdaUpdate().eq(SysJob::getJobName, jobName).remove();
    }

    @Override
    public void trigger(String jobName) {
        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            callHook(sysJob.getArtifactId(),"/elastic-job/trigger", Map.of("jobName", jobName));
        });
    }

    @Override
    public void register(String jobName) {
        lambdaQuery().eq(SysJob::getJobName, jobName).list().forEach(sysJob -> {
            callHook(sysJob.getArtifactId(),"/elastic-job/add", BeanUtil.beanToMap(sysJob));
        });
        updateJobStatus(jobName, JobStatusEnum.ENABLED);
    }


    private void callHook(String artifactId, String api, Map requestParams) {

        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances = discoveryClient.getInstances(artifactId);
        instances.forEach(instance -> {
            String url = instance.getUri().toString() + api;
            log.info("服务实例信息：{}", instance);
            try {
                log.info("请求参数：{}", requestParams);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map> entity = new HttpEntity<>(requestParams, headers);

                ResponseEntity<ResponseData> response = restTemplate.postForEntity(url, entity, ResponseData.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody().isSuccess()) {
                    log.info("Successfully called / {}", url);
                } else {
                    log.error("Failed to call / {}, rsp: {}", url, response.getBody());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Error calling /t on ,{} : {}", url, e.getMessage());
            }
        });
    }

    private void updateJobStatus(String jobName, JobStatusEnum status) {
        lambdaUpdate().set(SysJob::getJobState, status.name()).eq(SysJob::getJobName, jobName).update();
    }

}
