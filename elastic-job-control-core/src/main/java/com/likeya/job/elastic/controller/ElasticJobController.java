package com.likeya.job.elastic.controller;

import com.likeya.job.elastic.domain.Job;
import com.likeya.job.elastic.service.JobService;
import com.likeya.job.elastic.utils.ResponseData;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: like
 * @Date: 2024/9/13  15:57
 */
@RestController
@RequestMapping("/elastic-job")
public class ElasticJobController {

    @Autowired
    private JobService jobService;
    /**
     * 添加动态任务（适用于脚本逻辑已存在的情况，只是动态添加了触发的时间）
     * @param job	任务信息
     * @return
     */
    @PostMapping("/add")
    public ResponseData addJob(@RequestBody @Valid Job job, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ResponseData.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            jobService.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail(String.format("add error: %s", e.getMessage()));
        }
        return ResponseData.succeed();
    }



    @PostMapping("/enable")
    public ResponseData enable(@RequestBody Job job) {
        if (StringUtils.isEmpty(job.getJobName())) {
            ResponseData.fail("You must add a JobName");
        }
        jobService.enable(job.getJobName());
        return ResponseData.succeed();
    }

    @PostMapping("/disable")
    public ResponseData disable(@RequestBody Job job) {
        if (StringUtils.isEmpty(job.getJobName())) {
            ResponseData.fail("You must add a JobName");
        }
        jobService.disable(job.getJobName());
        return ResponseData.succeed();
    }

    @PostMapping("/shutdown")
    public Object shutdown(@RequestBody Job job) {
        if (StringUtils.isEmpty(job.getJobName())) {
            ResponseData.fail("You must add a JobName");
        }
        jobService.shutdown(job.getJobName());
        return ResponseData.succeed();
    }

    @PostMapping("/remove")
    public ResponseData removeJob(@RequestBody Job job) {
        if (StringUtils.isEmpty(job.getJobName())) {
            ResponseData.fail("You must add a JobName");
        }
        jobService.remove(job.getJobName());
        return ResponseData.succeed();
    }

    @PostMapping("/trigger")
    public ResponseData trigger(@RequestBody Job job) {
        if (StringUtils.isEmpty(job.getJobName())) {
            ResponseData.fail("You must add a JobName");
        }
        jobService.trigger(job.getJobName());
        return ResponseData.succeed();
    }

}
