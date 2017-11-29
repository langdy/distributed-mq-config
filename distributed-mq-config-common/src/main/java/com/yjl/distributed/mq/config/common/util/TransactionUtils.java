package com.yjl.distributed.mq.config.common.util;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Spring 事务管理工具类
 *
 * @author : zhaoyc
 * @date : 2016/12/23
 */
public class TransactionUtils {

    /**
     * 手动进行回滚事务. 接口中如果 try catch 异常无法回滚时,这手动调用回滚处理
     */
    public static void rollback() {
        TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
        if (null != transactionStatus) {
            transactionStatus.setRollbackOnly();
        }
    }
}
