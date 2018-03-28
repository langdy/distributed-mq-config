package com.yjl.distributed.mq.config.common.util;

/**
 * URL相关上下文
 * 
 * @author WuJianYing
 * @since 2018年3月16日
 *
 */
public class UrlContextUtils {
    /**
     * URL分隔符
     */
    public final static String URL_SEPARATOR = "/";

    /**
     * API前缀，带 “/”
     */
    public final static String URL_PREFIX_APP_1 = "/api";

    /**
     * API前缀，不带 “/”
     */
    public final static String URL_PREFIX_APP_2 = "api";

    public final static String PLACEHOLDER_TENANT_CODE = "{tenantCode}";

    /**
     * 存储当前请求租户代码
     */
    private final static ThreadLocal<String> TENANT_CODE = new ThreadLocal<String>();

    public static void setTenant(String tenantCode) {
        TENANT_CODE.set(tenantCode);
    }

    public static String getTenant() {
        return TENANT_CODE.get();
    }

    public static void delTenant() {
        TENANT_CODE.remove();
    }

}
