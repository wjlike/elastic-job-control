package com.likeya.job.elastic.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.likeya.job.elastic.service.entity.JobExecutionLog;
import com.likeya.job.elastic.utils.PageVO;


/**
 * 作业执行日志  Service
 *
 * @author like
 * @date 2024-10-14 17:09:45
 */
public interface JobExecutionLogService extends IService<JobExecutionLog> {

    /**
     * 分页列表
     * @param pageVo
     * @return
    */
    IPage<JobExecutionLog> pageList(PageVO<JobExecutionLog> pageVo);

}
