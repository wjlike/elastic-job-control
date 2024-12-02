package com.likeya.job.elastic.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.likeya.job.elastic.domain.SysJob;
import com.likeya.job.elastic.utils.PageVO;


/**
 * 表用于存储关于Elastic Jobs的信息 Service
 *
 * @author like
 * @date 2024-09-20 10:05:37
 */
public interface SysJobService extends IService<SysJob> {

    /**
     * 分页列表
     * @param pageVo
     * @return
    */
    IPage<SysJob> pageList(PageVO<SysJob> pageVo);

    /**
     * 保存表用于存储关于Elastic Jobs的信息
     * @param sysJob
     * @return
    */
    Boolean saveSysJob(SysJob sysJob);

    /**
     * 修改表用于存储关于Elastic Jobs的信息
     * @param sysJob
     * @return
     */
    Boolean updateSysJob(SysJob sysJob);

    /**
     * 启用
     * @param jobName
     */
    void enable(String jobName);

    /**
     * 禁用
     * @param jobName
     */
    void disable(String jobName);

    /**
     * 关闭
     * @param jobName
     */
    void shutdown(String jobName);

    /**
     * 移除
     * @param jobName
     */
    void remove(String jobName);

    /**
     * 触发
     * @param jobName
     */
    void trigger(String jobName);

    /**
     * 注册
     * @param jobName
     */
    void register(String jobName);
}
