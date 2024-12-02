package com.likeya.job.elastic.service.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.likeya.job.elastic.service.entity.JobStatusTraceLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作业状态跟踪日志Mapper
 *
 * @author like
 * @date 2024-10-14 17:09:49
 */
@Mapper
@DS("moss_tools")
public interface JobStatusTraceLogMapper extends BaseMapper<JobStatusTraceLog> {
}
