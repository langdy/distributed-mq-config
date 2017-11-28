package com.yjl.distributed.mq.config.common.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.yjl.distributed.mq.config.common.util.DateFormatStyleEnum;
import com.yjl.distributed.mq.config.common.util.DateUtilsPlus;

/**
 * @author : zhaoyc
 * @date : 17/7/17
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		for (DateFormatStyleEnum formatStyle : DateFormatStyleEnum.values()) {
			return DateUtilsPlus.formatStringByStyle(source.trim(), formatStyle.getDateStyle());
		}
		return DateUtilsPlus.formatStringByStyle(source.trim(), DateFormatStyleEnum.CN_DATE_BASIC_STYLE.getDateStyle());
	}
}
