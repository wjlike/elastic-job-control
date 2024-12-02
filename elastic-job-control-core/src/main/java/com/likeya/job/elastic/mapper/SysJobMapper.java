package com.likeya.job.elastic.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.likeya.job.elastic.domain.SysJob;
import org.apache.ibatis.annotations.Mapper;


/**
 * 表用于存储关于Elastic Jobs的信息
 *
 * @author like
 * @date 2024-09-20 10:05:37
 */
@Mapper
@DS("master")
public interface SysJobMapper extends BaseMapper<SysJob> {
}
