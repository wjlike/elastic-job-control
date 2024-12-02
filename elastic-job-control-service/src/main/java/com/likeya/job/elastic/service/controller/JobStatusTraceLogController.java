package com.likeya.job.elastic.service.controller;


import com.likeya.job.elastic.service.entity.JobStatusTraceLog;
import com.likeya.job.elastic.service.service.JobStatusTraceLogService;
import com.likeya.job.elastic.utils.PageVO;
import com.likeya.job.elastic.utils.ResponseData;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 作业状态跟踪日志 Controller
 * @author like
 * @date 2024-10-14 17:09:49
 */
@Log4j2
@RestController
@RequestMapping("job-status-trace-log")
@RequiredArgsConstructor
public class JobStatusTraceLogController {

    private final JobStatusTraceLogService jobStatusTraceLogService;

    /**
     * 分页列表
     */
    @PostMapping("/pageList")
    public ResponseData list(@RequestBody PageVO<JobStatusTraceLog> pageVo){
        return ResponseData.succeed(jobStatusTraceLogService.pageList(pageVo));
    }

    /**
     * 信息
     */
    @GetMapping("/detail/{id}")
    public ResponseData<JobStatusTraceLog> getDetail(@PathVariable("id")Long id){
        return ResponseData.succeed(jobStatusTraceLogService.getById(id));
    }
}
