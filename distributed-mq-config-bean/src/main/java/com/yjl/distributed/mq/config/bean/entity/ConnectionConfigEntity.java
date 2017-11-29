package com.yjl.distributed.mq.config.bean.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 连接配置表
 * </p>
 *
 * @author zhaoyc@1109
 * @since 2017-11-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("connection_config")
public class ConnectionConfigEntity extends Model<ConnectionConfigEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * broker集群唯一标志
     */
    @TableField("broker_key")
    private String brokerKey;
    /**
     * 集群地址
     */
    @TableField("broker_url")
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
    @TableField("max_connections")
    private Integer maxConnections;
    /**
     * 每个连接中使用的最大活动会话数
     */
    @TableField("maximum_active_session")
    private Integer maximumActiveSession;
    /**
     * 连接池中线程池大小
     */
    @TableField("max_thread_pool_size")
    private Integer maxThreadPoolSize;
    /**
     * 状态:ENABLE-可用，DISABLE-不可用，DELETE-删除
     */
    private String status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
