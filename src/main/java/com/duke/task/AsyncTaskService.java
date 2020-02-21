package com.duke.task;

import com.duke.entity.mongodb.SysLog;
import com.duke.util.CheckUtil;
import com.duke.util.JSONUtil;
import com.duke.util.RandomCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * @Title 异步任务类  主要服务于 异步日志持久化
 * @ClassName LoginUser
 * @Author duke
 * @Date 2018/9/13
 */
@Service
public class AsyncTaskService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${spring.mongodb.user.path.table.web.name}")
    String webLogTableName;
    @Value("${spring.mongodb.user.path.table.web.error.name}")
    String webErrorLogTableName;

    @Value("${spring.mongodb.user.path.table.app.name}")
    String appLogTableName;
    @Value("${spring.mongodb.user.path.table.app.error.name}")
    String appErrorLogTableName;

    @Value("${spring.mongodb.user.path.table.san.name}")
    String sanLogTableName;
    @Value("${spring.mongodb.user.path.table.san.error.name}")
    String sanErrorLogTableName;

    @Value("${spring.mongodb.user.path.table.error.name}")
    String errorLogTableName;
    @Async
    public void executeAsyncLogTask(SysLog sysLog){
        sysLog.setLogId(RandomCodeUtil.getTimeCode());
        sysLog.setPort(CheckUtil.integerIsNull(sysLog.getPort())?0:sysLog.getPort());
        logger.info("日志入库调用：{}", JSONUtil.toJson(sysLog));
        if(sysLog.getPort() == 1){
            if(sysLog.getType() == 1){
                mongoTemplate.insert(sysLog,webLogTableName);
            }else {
                mongoTemplate.insert(sysLog,webErrorLogTableName);
            }
        }else if (sysLog.getPort() == 2) {
            if(sysLog.getType() == 1){
                mongoTemplate.insert(sysLog,appLogTableName);
            }else {
                mongoTemplate.insert(sysLog,appErrorLogTableName);
            }
        }else if(sysLog.getPort() == 3) {
            if(sysLog.getType() == 1){
                mongoTemplate.insert(sysLog,sanLogTableName);
            }else {
                mongoTemplate.insert(sysLog,sanErrorLogTableName);
            }
        }else {
            mongoTemplate.insert(sysLog,errorLogTableName);
        }
    }


    @Async
    public void executeAsyncRegisterProduct(String merchantNo,Integer id) {
        logger.info("商户产品注册：{}", merchantNo);

        }
}





