package com.likeya.job.elastic.service.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;

/**
 * Elastic job 执行日志
 *
 * @author like
 * @date 2024-10-14 17:09:45
 */
@Data
@TableName("job_execution_log")
public class JobExecutionLog{

	private String id;

	/**
	 * $column.comments
	 */
	private String jobName;
	/**
	 * $column.comments
	 */
	private String taskId;
	/**
	 * $column.comments
	 */
	private String hostname;
	/**
	 * $column.comments
	 */
	private String ip;
	/**
	 * $column.comments
	 */
	private Integer shardingItem;
	/**
	 * $column.comments
	 */
	private String executionSource;
	/**
	 * $column.comments
	 */
	private String failureCause;
	/**
	 * $column.comments
	 */
	private Boolean isSuccess;
	/**
	 * $column.comments
	 */
	private Date startTime;
	/**
	 * $column.comments
	 */
	private Date completeTime;


}
