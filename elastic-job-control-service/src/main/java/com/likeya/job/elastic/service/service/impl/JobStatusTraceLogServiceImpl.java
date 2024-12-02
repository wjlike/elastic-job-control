package com.likeya.job.elastic.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.likeya.job.elastic.service.entity.JobStatusTraceLog;
import com.likeya.job.elastic.service.mapper.JobStatusTraceLogMapper;
import com.likeya.job.elastic.service.service.JobStatusTraceLogService;
import com.likeya.job.elastic.utils.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:  作业状态跟踪日志 实现类
 * @author like
 * @date 2024-10-14 17:09:49
 */
@Slf4j
@Service
public class JobStatusTraceLogServiceImpl extends ServiceImpl<JobStatusTraceLogMapper, JobStatusTraceLog> implements JobStatusTraceLogService {

    @Override
    public IPage pageList(PageVO<JobStatusTraceLog> pageVo) {
        LambdaQueryWrapper<JobStatusTraceLog> queryWrapper = new LambdaQueryWrapper<>(JobStatusTraceLog.class).setEntity(pageVo.getEntity());
        queryWrapper.orderByDesc(JobStatusTraceLog::getCreationTime);
        return baseMapper.selectPage(pageVo, queryWrapper);
    }

}
