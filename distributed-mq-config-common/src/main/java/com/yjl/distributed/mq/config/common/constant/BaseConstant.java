package com.yjl.distributed.mq.config.common.constant;

/**
 * 全局常量
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年9月4日 下午8:35:36
 */
public interface BaseConstant {
    /**
     * 祖先id
     */
    Long ROOT_ID = 0L;

    /** 主键key **/
    String KEY_ID = "id";

    /**
     * 整数0
     */
    Integer INTEGER_ZERO = 0;

    /**
     * 整数1
     */
    Integer INTEGER_ONE = 1;

    /** 通配符 **/
    String WILDCARD_ALL = "*";

    /**
     * UUID表示符
     */
    String UUID_KEY = "uuid";

    /**
     * 默认编码
     */
    String DEFAULT_CHARSET = "UTF-8";
}
