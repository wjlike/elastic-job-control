## elastic job 分布式管理工具 demo

### [elastic-job-control-core](elastic-job-control-core) 核心包
    
    每个Job模块须引用该包

     public class JobTest extends MySimpleJob{
        @Override
        public void doExecute(ShardingContext shardingContext){
            //do something...  
        }
     }
    

### [elastic-job-control-service](elastic-job-control-service) elastic job 管理端

