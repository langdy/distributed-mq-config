package com.yjl.distributed.mq.config;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动执行
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年9月4日 下午8:59:34
 */
@Component
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        LogManager.getLogger().info(">>>>>>>>>>>>>>>mq-config服务启动完成<<<<<<<<<<<<<");
    }

}
