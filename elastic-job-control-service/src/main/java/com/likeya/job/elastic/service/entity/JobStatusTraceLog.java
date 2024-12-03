package com.likeya.job.elastic.service.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 作业状态跟踪日志
 *
 * @author like
 * @date 2024-10-14 17:09:49
 */
@Data
@TableName("job_status_trace_log")
public class JobStatusTraceLog{

	private String id;
	/**
	 * 作业名称
	 */
	private String jobName;
	/**
	 *
	 */
	private String originalTaskId;
	/**
	 *
	 */
	private String taskId;
	/**
	 *
	 */
	private String slaveId;
	/**
	 *
	 */
	private String source;
	/**
	 *
	 */
	private String executionType;
	/**
	 *
	 */
	private String shardingItem;
	/**
	 *
	 */
	private String state;
	/**
	 *
	 */
	private String message;
	/**
	 *
	 */
	private Date creationTime;
}
