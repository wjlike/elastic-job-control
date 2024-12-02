package com.likeya.job.elastic.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.likeya.job.elastic.service.entity.JobExecutionLog;
import com.likeya.job.elastic.service.mapper.JobExecutionLogMapper;
import com.likeya.job.elastic.service.service.JobExecutionLogService;
import com.likeya.job.elastic.utils.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:  作业执行日志 实现类
 * @author like
 * @date 2024-10-14 17:09:45
 */
@Slf4j
@Service
public class JobExecutionLogServiceImpl extends ServiceImpl<JobExecutionLogMapper, JobExecutionLog> implements JobExecutionLogService {

    @Override
    public IPage pageList(PageVO<JobExecutionLog> pageVo) {
        LambdaQueryWrapper<JobExecutionLog> queryWrapper = new LambdaQueryWrapper<>(JobExecutionLog.class).setEntity(pageVo.getEntity());
        queryWrapper.orderByDesc(JobExecutionLog::getStartTime);
        return baseMapper.selectPage(pageVo, queryWrapper);
    }
}
