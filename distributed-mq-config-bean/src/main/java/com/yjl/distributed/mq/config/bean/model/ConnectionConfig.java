package com.yjl.distributed.mq.config.bean.model;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 连接配置
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年11月16日 下午6:02:30
 */
@Data
@Accessors(chain = true)
public class ConnectionConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1620619519130349011L;

    private Long id;
    /**
     * broker集群唯一标志
     */
    private String brokerKey;
    /**
     * 集群地址
     */
    private String brokerUrl;
    /**
     * 登录用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 最大连接数
     */
    private Integer maxConnections;
    /**
     * 每个连接中使用的最大活动会话数
     */
    private Integer maximumActiveSession;
    /**
     * 连接池中线程池大小
     */
    private Integer maxThreadPoolSize;
    /**
     * 状态:ENABLE-可用，DISABLE-不可用，DELETE-删除
     */
    private String status;

}
