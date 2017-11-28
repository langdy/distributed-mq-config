package com.yjl.distributed.mq.config.common.util;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午11:46:56
 */
public class ValidatedUtils {

	/**
	 * hibernate方式的参数校验
	 * 
	 * @param result
	 * @throws Exception
	 */
	public static void valid(BindingResult result) throws ValidationException {
		if (result.hasErrors()) {
			StringBuilder errors = new StringBuilder();
			List<ObjectError> errorList = result.getAllErrors();
			if (errorList == null || errorList.size() == 0) {
				return;
			}
			for (ObjectError error : errorList) {
				errors.append(error.getDefaultMessage()).append(",");
			}

			// 删除最后一个逗号
			errors.delete(errors.length() - 1, errors.length());

			throw new ValidationException(errors.toString());
		}
	}

}
