package com.yjl.distributed.mq.config.common.util;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import com.yjl.distributed.mq.config.common.constant.BaseConstant;
import com.yjl.distributed.mq.config.common.exception.DaoException;
import com.yjl.distributed.mq.config.common.exception.ServiceException;

/**
 * 断言 使用异常流程控制,异常控制流程比if else '状态码'控制可读性更强
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午11:31:48
 */
public class AssertUtils {

    /**
     * service 层断言
     *
     * @param condition : 断言条件
     * @param message : 错误信息
     * @throws ServiceException 如果条件为true,则会抛Service异常(在GlobalResponseControllerAdvice中会有统一异常处理)
     */
    public static void isTrue(boolean condition, String message) {
        if (condition) {
            throw new ServiceException(message);
        }
    }


    /**
     * valid（参数校验） 断言
     *
     * @param condition : 断言条件
     * @param message : 错误信息
     * @throws ValidationException
     *         如果条件为true,则会抛Validation异常(在GlobalResponseControllerAdvice中会有统一异常处理)
     */
    public static void validIsTrue(boolean condition, String message) {
        if (condition) {
            throw new ValidationException(message);
        }
    }


    /**
     * dao 层断言
     *
     * @param condition : 断言条件
     * @param message : 错误信息
     * @throws DaoException 如果条件为true,则会抛Dao异常(在GlobalResponseControllerAdvice中会有统一异常处理)
     */
    public static void daoIsTrue(boolean condition, String message) {
        if (condition) {
            throw new DaoException(message);
        }
    }

    /**
     * 断言: value为空字符串
     *
     * @param str 需要校验的字符串
     * @param message 错误信息
     */
    public static void assertEmpty(String str, String message) {
        AssertUtils.validIsTrue(StringUtils.isEmpty(str), message);
    }

    /**
     * 断言 : 传入的ID 为空或等于0
     *
     * @param id ID
     * @param message 错误信息
     */
    public static void assertWrongId(Long id, String message) {
        AssertUtils.validIsTrue(id == null || id.compareTo(BaseConstant.ROOT_ID) == 0, message);
    }

    /**
     * 断言:对象为空
     *
     * @param obj obj
     * @param message 错误信息
     */
    public static void assertNull(Object obj, String message) {
        AssertUtils.validIsTrue(obj == null, message);
    }

    /**
     * 断言:两个日期(一个起始日期, 一个截止日期) 之间不存在间隙 或 其实日期 > 截止日期
     *
     * @param beforeDate 起始日期
     * @param afterDate 截止日期
     * @param message 错误信息
     */
    public static void validateDateOffset(Date beforeDate, Date afterDate, String message) {

        AssertUtils.validIsTrue(beforeDate == null || afterDate == null, message);
        AssertUtils.validIsTrue(beforeDate.compareTo(afterDate) >= 0, message);

    }

    /**
     * 断言:传入的Decimal不是正数
     *
     * @param bigDecimal decimal
     * @param message 字段名
     */
    public static void assertNotPositiveNumber(BigDecimal bigDecimal, String message) {
        assertNull(bigDecimal, message);
        AssertUtils.validIsTrue(bigDecimal.compareTo(BigDecimal.ZERO) <= 0, message);
    }

    /**
     * 断言: 传入的Integer不是正数
     *
     * @param integer integer
     * @param message 错误信息
     */
    public static void assertNotPositiveNumber(Integer integer, String message) {
        assertNull(integer, message);
        AssertUtils.validIsTrue(integer <= 0, message);
    }

}
