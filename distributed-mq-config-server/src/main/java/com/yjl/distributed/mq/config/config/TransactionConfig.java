package com.yjl.distributed.mq.config.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext-tx.xml")
public class TransactionConfig {
	// 声明式事务
}
