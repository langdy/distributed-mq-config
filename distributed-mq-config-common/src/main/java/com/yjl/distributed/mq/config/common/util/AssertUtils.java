package com.yjl.distributed.mq.config.common.util;

import com.yjl.distributed.mq.config.common.exception.CaptchaException;
import com.yjl.distributed.mq.config.common.exception.DaoException;
import com.yjl.distributed.mq.config.common.exception.ForbiddenException;
import com.yjl.distributed.mq.config.common.exception.ResourceNotFoundException;
import com.yjl.distributed.mq.config.common.exception.ServiceException;

/**
 * 断言 使用异常流程控制,异常控制流程比if else '状态码'控制可读性更强
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午11:31:48
 */
public class AssertUtils {


	/**
	 * 如果条件为<code>true</code> throw {@link ResourceNotFoundException}
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws ResourceNotFoundException
	 */
	public static void assertResourceNotFoundIsTrue(boolean condition, String message) {
		if (condition) {
			throw new ResourceNotFoundException(message);
		}

	}


	/**
	 * 如果条件为<code>true</code> throw {@link CaptchaException}
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws CaptchaException
	 */
	public static void assertCaptchaIsTrue(boolean condition, String message) {
		if (condition) {
			throw new CaptchaException(message);
		}
	}

	/**
	 * 如果条件为<code>true</code> throw {@link ForbiddenException}
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws ForbiddenException
	 */
	public static void assertPermissionIsTrue(boolean condition, String message) throws ForbiddenException {
		if (condition) {
			throw new ForbiddenException(message);
		}
	}

	/**
	 * service 层断言
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws ServiceException
	 */
	public static void isTrue(boolean condition, String message) {
		assertServiceException(condition, message);
	}


	/**
	 * dao 层断言
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws DaoException
	 */
	public static void assertDaoIsTrue(boolean condition, String message) {
		daoServiceException(condition, message);
	}

	/**
	 * 断言
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws DaoException 如果条件为<code>true</code>,则会抛Service异常(异常为运行时异常,在Spring中会有统一异常处理)
	 */
	private static void assertServiceException(boolean condition, String message) {
		if (condition) {
			throw new ServiceException(message);
		}
	}

	/**
	 * 断言
	 *
	 * @param condition : 断言条件
	 * @param message : 错误信息
	 * @throws DaoException 如果条件为<code>true</code>,则会抛Dao异常(异常为运行时异常,在Spring中会有统一异常处理)
	 */
	private static void daoServiceException(boolean condition, String message) {
		if (condition) {
			throw new DaoException(message);
		}
	}


}
