package com.likeya.job.elastic.listener;


import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;
import org.apache.shardingsphere.elasticjob.lite.api.listener.AbstractDistributeOnceElasticJobListener;

/**
 *
 */
public class MossElasticJobListener extends AbstractDistributeOnceElasticJobListener {

    public static final String MOSS_ELASTIC_JOB_LISTENER = "mossElasticJobListener";

    private long beginTime = 0;

    public MossElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }


    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();

        System.out.println("===>{} JOB BEGIN TIME: {} <=== " + shardingContexts.getJobName() + beginTime);
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        System.out.println("===>{} JOB END TIME: {},TOTAL CAST: {} <===" + shardingContexts.getJobName() + endTime + (endTime - beginTime));
    }

    @Override
    public String getType() {
        return MOSS_ELASTIC_JOB_LISTENER;
    }
}
