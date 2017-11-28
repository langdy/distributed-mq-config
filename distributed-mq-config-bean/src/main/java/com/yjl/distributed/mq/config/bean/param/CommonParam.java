package com.yjl.distributed.mq.config.bean.param;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 页面参数父类
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年9月1日 下午3:11:55
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CommonParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1050021779480485005L;

	/** 默认分页起始值 */
	public static final int DEFAULT_PAGE_NUM = 1;

	/** 默认分页大小值 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 页码，1开始 */
	private int offset;

	/** 页大小 */
	private int limit;

	/**
	 * 查询开始时间
	 */
	private String startTime;

	/**
	 * 查询结束时间
	 */
	private String endTime;

	/**
	 * 排序字段,使用时必须调用方法：{@link com.yjl.distributed.mq.config.bean.param.CommonQuery#filterSort} 做合法性检查
	 */
	private String sort;

	/**
	 * 根据开始时间，结束时间计算得出：DataCode.DAY:代表按天统计 DataCode.HOUR:按小时统计，把sql格式化字符串传到sql语句
	 */
	private String groupByType;


	public CommonParam() {
		this(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
	}

	public CommonParam(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}


	/**
	 * 是否设置默认时间
	 * 
	 * @param defaultDate true-设置默认时间，false-不设置默认时间
	 * @throws Exception
	 */
	public void diffDate(boolean defaultDate) {
		if (defaultDate) {
			// 如果时间为空，则设置默认时间为当天
			this.diffDate();
		} else {
			// 不设置默认时间，如果时间为空则直接返回
			if (this.getStartTime() == null && this.getEndTime() == null) {
				return;
			}
			this.setDate();
		}
	}

	/**
	 * 开始结束时间规整，默认取当天时间，支持精确到天和秒，设置groupByType类型，仅支持按小时和按天
	 * 
	 * @throws Exception
	 */
	private void diffDate() {

		// 如果当前开始时间和结束时间都为空，给出系统默认时间，按小时统计
		if (this.getStartTime() == null && this.getEndTime() == null) {
			String nowDate = DateFormatUtils.format(new Date(), DateFormatStyle.CN_DATE_BASIC_STYLE4.getDateStyle());
			this.setStartTime(nowDate + " 00:00:00");
			this.setEndTime(nowDate + " 23:59:59");
			this.setGroupByType(DataCode.HOUR.getFormat());
			return;
		}

		this.setDate();
	}



	/**
	 * 开始结束时间规整，支持精确到天和秒，设置groupByType类型，仅支持按小时和按天
	 * 
	 * @throws Exception
	 */
	private void setDate() {
		try {
			// 精确到天的结束时间加上当天24小时，方便sql统计全天数据
			if (this.getEndTime() != null && this.getEndTime().length() <= 10) {
				this.setEndTime(this.getEndTime() + " 23:59:59");
			}

			// 开始或者结束时间为空就不需要计算分组，直接返回
			if (this.getStartTime() == null || this.getEndTime() == null) {
				return;
			}

			Date startDate = DateUtils.parseDate(this.getStartTime(), Locale.getDefault(),
					DateFormatStyle.CN_DATE_BASIC_STYLE.getDateStyle(),
					DateFormatStyle.CN_DATE_BASIC_STYLE4.getDateStyle());
			Date endDate = DateUtils.parseDate(this.getEndTime(), Locale.getDefault(),
					DateFormatStyle.CN_DATE_BASIC_STYLE.getDateStyle(),
					DateFormatStyle.CN_DATE_BASIC_STYLE4.getDateStyle());

			if (startDate == null || endDate == null) {
				throw new Exception("开始时间或结束时间不允许为空");
			}
			if (!startDate.before(endDate)) {
				throw new Exception("开始时间不允许大于结束时间");
			}

			// 根据时间计算统计类型
			initGroupByType(startDate, endDate);
		} catch (Exception e) {

		}

	}

	/**
	 * 根据时间计算统计类型
	 */
	public void initGroupByType(Date startDate, Date endDate) {
		// 1.小于等于1天，按小时统计
		if (DateUtils.addDays(startDate, 1).getTime() >= endDate.getTime()) {
			this.setGroupByType(DataCode.HOUR.getFormat());
		}
		// 2.小于等于一个月，按天统计
		else if (DateUtils.addMonths(startDate, 1).getTime() >= endDate.getTime()) {
			this.setGroupByType(DataCode.DAY.getFormat());
		}
		// 3.大于一个月，按月统计
		else {
			this.setGroupByType(DataCode.MONTH.getFormat());
		}
	}


	/**
	 * 检查排序字段的合法性,合法格式：field [desc,asc]
	 * 
	 * @param clazz 需要排序的属性对应的业务对象
	 */
	public void filterSort(Class<?> clazz) {
		String sort = this.getSort();
		if (sort == null) {
			return;
		}

		// 先把desc或者asc去掉(只取空格前的部分)
		sort = sort.trim();
		String[] sorts = sort.split(" ");
		if (sorts.length >= 3) {
			// 非法输入，则设置排序为空
			this.setSort(null);
			return;
		}

		if (sorts.length == 2 && !"desc".equalsIgnoreCase(sorts[1]) && !"asc".equalsIgnoreCase(sorts[1])) {
			// 非法输入，则设置排序为空
			this.setSort(null);
			return;
		}

		sort = sorts[0];

		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (sort.equalsIgnoreCase(field.getName())) {
				return; // 合法排序字段（即为业务对象属性）
			}
		}

		// 非法输入，则设置排序为空
		this.setSort(null);
	}
}


enum DataCode {
	//
	SECOND("0", "%Y-%m-%d %H:%i:%S", "秒"),
	//
	MINUTE("1", "%Y-%m-%d %H:%i", "分钟"),
	//
	HOUR("2", "%Y-%m-%d %H", "小时"),
	//
	DAY("3", "%Y-%m-%d", "天"),
	//
	MONTH("4", "%Y-%m", "月"),
	//
	YEAR("5", "%Y", "年"),
	//
	WEEK("6", "%Y-%u", "周");

	private final String code;
	private final String format;
	private final String describe;

	DataCode(String code, String format, String describe) {
		this.code = code;
		this.describe = describe;
		this.format = format;
	}

	public String getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}

	public String getFormat() {
		return format;
	}
}


@Getter
enum DateFormatStyle {

	//
	CN_DATE_BASIC_STYLE("yyyy-MM-dd HH:mm:ss"),
	//
	CN_DATE_BASIC_STYLE2("yyyy/MM/dd HH:mm:ss"),
	//
	CN_DATE_BASIC_STYLE3("yyyy/MM/dd"),
	//
	CN_DATE_BASIC_STYLE4("yyyy-MM-dd"),
	//
	CN_DATE_BASIC_STYLE5("yyyyMMdd"),
	//
	CN_DATE_BASIC_STYLE6("yyyy-MM"),
	//
	CN_DATE_BASIC_STYLE7("yyyyMM"),
	//
	DATE_TIMESTAMP_STYLE("yyyyMMddHHmmss"),
	//
	DATE_TIMESTAMPS_STYLE("yyyyMMddHHmmssSSS"),
	//
	DATE_TIMESTAMPS_STYLE_HOUS("yyyy-MM-dd HH");

	private String dateStyle;

	DateFormatStyle(String dateStyle) {
		this.dateStyle = dateStyle;
	}


}
