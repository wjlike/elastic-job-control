package com.likeya.job.elastic.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.likeya.job.elastic.service.entity.JobStatusTraceLog;
import com.likeya.job.elastic.utils.PageVO;


/**
 * 作业状态跟踪日志 Service
 *
 * @author like
 * @date 2024-10-14 17:09:49
 */
public interface JobStatusTraceLogService extends IService<JobStatusTraceLog> {

    /**
     * 分页列表
     * @param pageVo
     * @return
    */
    IPage<JobStatusTraceLog> pageList(PageVO<JobStatusTraceLog> pageVo);

}
