package com.likeya.job.elastic.service.controller;



import com.likeya.job.elastic.service.entity.JobExecutionLog;
import com.likeya.job.elastic.service.service.JobExecutionLogService;
import com.likeya.job.elastic.utils.PageVO;
import com.likeya.job.elastic.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 作业执行日志 Controller
 * @author like
 * @date 2024-10-14 17:09:45
 */
@Log4j2
@RestController
@RequestMapping("job-execution-log")
@RequiredArgsConstructor
public class JobExecutionLogController {

    private final JobExecutionLogService jobExecutionLogService;

    /**
     * 分页列表
     */
    @PostMapping("/pageList")
    public ResponseData list(@RequestBody PageVO<JobExecutionLog> pageVo){
        return ResponseData.succeed(jobExecutionLogService.pageList(pageVo));
    }

}
