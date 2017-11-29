package com.yjl.distributed.mq.config.common.exception;

/**
 * @author : zhaoyc
 * @date : 2017/6/27
 */
public class CaptchaException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -3294661539806096567L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    protected CaptchaException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
