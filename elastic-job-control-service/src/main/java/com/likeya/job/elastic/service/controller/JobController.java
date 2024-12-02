package com.likeya.job.elastic.service.controller;

import com.likeya.job.elastic.domain.SysJob;
import com.likeya.job.elastic.service.service.SysJobService;
import com.likeya.job.elastic.utils.PageVO;
import com.likeya.job.elastic.utils.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 动态任务添加控制器
 *
 * <p>可以用于同一个任务，需要不同的时间来进行触发场景<p>
 */
@Slf4j
@RestController
@RequestMapping("/job-management")
public class JobController {

	@Autowired
	private SysJobService jobService;

	/**
	 * 分页列表
	 * @param pageVo 分页参数
	 * @return 分页结果
	 */
	@PostMapping("/pageList")
	public ResponseData list(@RequestBody PageVO<SysJob> pageVo) {
		return ResponseData.succeed(jobService.pageList(pageVo));
	}

	/**
	 * 添加动态任务（适用于脚本逻辑已存在的情况，只是动态添加了触发的时间）
	 * @param job 任务信息
	 * @param bindingResult 校验结果
	 * @return 操作结果
	 */
	@PostMapping("/add")
	public ResponseData addJob(@RequestBody @Valid SysJob job, BindingResult bindingResult) {
		return handleJobOperation(() -> jobService.saveSysJob(job), bindingResult, "add");
	}

	/**
	 * 编辑任务
	 * @param job 任务信息
	 * @param bindingResult 校验结果
	 * @return 操作结果
	 */
	@PostMapping("/edit")
	public ResponseData edit(@RequestBody @Valid SysJob job, BindingResult bindingResult) {
		return handleJobOperation(() -> jobService.updateById(job), bindingResult, "edit");
	}

	/**
	 * 注册任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/register")
	public ResponseData register(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.register(jobName), "register");
	}

	/**
	 * 启用任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/enable")
	public ResponseData enable(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.enable(jobName), "enable");
	}

	/**
	 * 禁用任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/disable")
	public ResponseData disable(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.disable(jobName), "disable");
	}

	/**
	 * 关闭任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/shutdown")
	public ResponseData shutdown(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.shutdown(jobName), "shutdown");
	}

	/**
	 * 移除任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/remove")
	public ResponseData removeJob(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.remove(jobName), "remove");
	}

	/**
	 * 触发任务
	 * @param jobName 任务名称
	 * @return 操作结果
	 */
	@GetMapping("/trigger")
	public ResponseData trigger(@RequestParam("jobName") String jobName) {
		return handleJobOperation(() -> jobService.trigger(jobName), "trigger");
	}

	/**
	 * 处理任务操作
	 * @param jobOperation 任务操作
	 * @param bindingResult 校验结果
	 * @param operation 操作名称
	 * @return 操作结果
	 */
	private ResponseData handleJobOperation(Runnable jobOperation, BindingResult bindingResult, String operation) {
		if (bindingResult.hasErrors()) {
			return ResponseData.fail(bindingResult.getFieldError().getDefaultMessage());
		}
		return handleJobOperation(jobOperation, operation);
	}

	/**
	 * 处理任务操作
	 * @param jobOperation 任务操作
	 * @param operation 操作名称
	 * @return 操作结果
	 */
	private ResponseData handleJobOperation(Runnable jobOperation, String operation) {
		if (StringUtils.isEmpty(operation)) {
			return ResponseData.fail("You must add a JobName");
		}
		try {
			jobOperation.run();
			return ResponseData.succeed();
		} catch (Exception e) {
			log.error("{} error: {}", operation, e.getMessage(), e);
			return ResponseData.fail(String.format("%s error: %s", operation, e.getMessage()));
		}
	}
}
