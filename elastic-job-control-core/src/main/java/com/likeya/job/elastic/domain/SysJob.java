package com.likeya.job.elastic.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 表用于存储关于Elastic Jobs的信息
 *
 * @author like
 * @date 2024-09-20 10:05:37
 */
@Data
@TableName("sys_job")
public class SysJob {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;

	/**
	 * 项目Id
	 */
	private String artifactId;
	/**
	 * 作业名称
	 */
	private String jobName;
	/**
	 * 作业名称
	 */
	private String jobClass;
	/**
	 * 用于调度的Cron表达式
	 */
	private String cron;
	/**
	 * 作业参数
	 */
	private String jobParameter;
	/**
	 * 分片总数
	 */
	private Integer shardingTotalCount = 1;
	/**
	 * 每个分片项的参数
	 */
	private String shardingItemParameters;
	/**
	 * 作业类型（简单、数据流、脚本）
	 */
	private String jobType;
	/**
	 * 当前作业状态
	 */
	private String jobState;
	/**
	 * 失败次数
	 */
	private Integer failCount;
	/**
	 * 上一次执行的时间
	 */
	private Date lastExecutionTime;
	/**
	 * 下一次触发执行的时间
	 */
	private Date nextFireTime;
	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected LocalDateTime createTime;
	/**
	 * 创建人
	 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String createUser;

	/**
	 * 最后修改时间
	 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected LocalDateTime updateTime;
	/**
	 * 最后修改人
	 */
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String updateUser;

}
