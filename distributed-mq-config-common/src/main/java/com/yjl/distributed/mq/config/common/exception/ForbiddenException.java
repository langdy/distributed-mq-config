package com.yjl.distributed.mq.config.common.exception;

/**
 * 权限
 *
 * @author : zhaoyc
 * @date : 2017/6/22
 */
public class ForbiddenException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3902401000445776342L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
