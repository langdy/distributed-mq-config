package com.yjl.distributed.mq.config.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.yjl.distributed.mq.config.common.regex.RegexType;

/**
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午9:40:47
 */
public class DateUtilsPlus extends DateUtils {

	public static final String DATE_BASIC_STYLE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_NORMAL_STYLE = "yyyy-MM-dd";
	private static final Pattern DATE_BASIC_PATTERN = Pattern.compile(RegexType.DATE_BASIC);
	private static final Pattern DATE_BASIC_FORMAT_PATTERN = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");



	/**
	 * 得到当月最后一天的'时间'
	 *
	 * @return
	 */
	public static Date nowMonthLast() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		return now.getTime();
	}

	/**
	 * 得到当月最后一天
	 *
	 * @return min:1 , max:31
	 */
	public static int nowMonthLastDay() {
		return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据时间(Date类型)得到指定格式的日期字符串
	 *
	 * @return
	 */
	public static String formatDateByStyle(final Date date, final String dateStyle) {
		DateFormat format = new SimpleDateFormat(dateStyle);
		return format.format(date);
	}

	/**
	 * {@link #formatDateByStyle(Date , String)} 默认格式格式 : {@link DateFormatStyle#CN_DATE_BASIC_STYLE}
	 */
	public static String formatDateByStyle(final Date date) {
		return formatDateByStyle(date, DateFormatStyleEnum.CN_DATE_BASIC_STYLE.getDateStyle());
	}

	/**
	 * 根据时间类型的字符串得到指定格式的日期 <br/>
	 *
	 * @param date : 日期
	 * @param dateStyle | yyyy : 年 | MM : 月 | dd : 日 | HH : 时 | mm : 分 | ss : 秒
	 * @return
	 */
	public static Date formatStringByStyleToDate(final String date, final String dateStyle) {
		DateFormat format = new SimpleDateFormat(dateStyle);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}



	/**
	 * 得到两个日期之间的分钟差
	 * 
	 * <pre>
	 * DateUtil.getMinuteInterval(  2017-5-4 15:00:08 ,2017-5-4 15:51:08  ) = 51
	 * DateUtil.getMinuteInterval(  2017-5-4 15:00:34 ,2017-5-4 15:51:34  ) = 51
	 * DateUtil.getMinuteInterval(  2016-5-4 15:56:30 ,2017-5-4 15:56:30  ) = 525600
	 * </pre>
	 *
	 * @param a : Date 类型,不分前后顺序
	 * @param b : Date 类型,不分前后顺序
	 * @return 日期之间的分钟间隔
	 */
	public static long getMinuteInterval(Date a, Date b) {
		return Math.abs((a.getTime() - b.getTime()) / (1000 * 60));
	}

	/**
	 * 得天数间隔时间
	 * 
	 * <pre>
	 * DateUtil.getMinuteInterval(  2016-5-4 15:56:30 ,2017-5-4 15:56:30  ) = 365
	 * </pre>
	 *
	 * @param a : Date 类型,不分前后顺序
	 * @param b : Date 类型,不分前后顺序
	 * @return 日期之间的天数间隔
	 */
	public static long getDayInterval(Date a, Date b) {
		return Math.abs((a.getTime() - b.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 目前仅支持以下类型的判断
	 * <p>
	 * 2016-12-19 15:59:45 2016-12-19 2016/12/19 15:59:45 2016/12/19 20161219155945 20161219
	 *
	 * @param inputTime
	 * @return
	 */
	public static boolean isDate(final String inputTime) {
		Matcher matcher = DATE_BASIC_PATTERN.matcher(inputTime);
		if (!matcher.matches()) {
			return false;
		}
		matcher = DATE_BASIC_FORMAT_PATTERN.matcher(inputTime);
		if (matcher.matches()) {
			int year = Integer.parseInt(matcher.group(1));
			int month = Integer.parseInt(matcher.group(2));
			int date = Integer.parseInt(matcher.group(3));
			if (date > 28) {
				Calendar instance = Calendar.getInstance();
				instance.set(year, month - 1, 1);
				int lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
				return (lastDay >= date);
			}
		}
		return true;
	}

	/**
	 * 不是一个Date类型
	 *
	 * @param inputTime
	 * @return
	 * @see DateUtilsPlus#isDate(String)
	 */
	public static boolean isNotDate(final String inputTime) {
		return !isDate(inputTime);
	}

	/**
	 * 字符串转成Date类型
	 * <p>
	 * dateStyle:
	 * <table border="1" summary="Pattern Tokens">
	 * <tr>
	 * <th>character</th>
	 * <th>duration element</th>
	 * </tr>
	 * <tr>
	 * <td>y</td>
	 * <td>years</td>
	 * </tr>
	 * <tr>
	 * <td>M</td>
	 * <td>months</td>
	 * </tr>
	 * <tr>
	 * <td>d</td>
	 * <td>days</td>
	 * </tr>
	 * <tr>
	 * <td>H</td>
	 * <td>hours</td>
	 * </tr>
	 * <tr>
	 * <td>m</td>
	 * <td>minutes</td>
	 * </tr>
	 * <tr>
	 * <td>s</td>
	 * <td>seconds</td>
	 * </tr>
	 * <tr>
	 * <td>S</td>
	 * <td>milliseconds</td>
	 * </tr>
	 * </table>
	 *
	 * @param inputTime
	 * @param dateStyle
	 * @return
	 */
	public static Date formatStringByStyle(final String inputTime, final String dateStyle) {
		try {
			if (StringUtils.isBlank(inputTime)) {
				return null;
			}
			if (StringUtils.isBlank(dateStyle)) {
				return null;
			}
			DateFormat format = new SimpleDateFormat(dateStyle);
			return format.parse(inputTime);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * {@link #formatStringByStyle(String , String)}
	 */
	public static LocalDateTime formatStringByStyleToLocalDateTime(final String inputTime, final String dateStyle) {
		if (StringUtils.isBlank(inputTime)) {
			return null;
		}
		if (StringUtils.isBlank(dateStyle)) {
			return null;
		}
		return LocalDateTime.parse(inputTime, DateTimeFormatter.ofPattern(dateStyle));
	}

	/**
	 * 得到当前时间,字符串形式,默认格式:{@link DateFormatStyle#DATE_TIMESTAMP_STYLE}
	 */
	public static String currentTimeString() {
		return currentTimeString(DateFormatStyleEnum.DATE_TIMESTAMP_STYLE.getDateStyle());
	}

	/**
	 * 得到当前时间,字符串形式
	 *
	 * @param dateStyle {@link DateFormatStyle}
	 * @return
	 */
	public static String currentTimeString(String dateStyle) {
		return formatDateByStyle(Calendar.getInstance().getTime(), dateStyle);
	}

	/**
	 * 获取前 i 天日期
	 *
	 * @return
	 */
	public static LocalDate getPreviousDay(int i) {
		LocalDate today = LocalDate.now();
		return today.minus(i, ChronoUnit.DAYS);
	}



	/**
	 * 获取周第一天0点时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getWeekFirstDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取本周一0点时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getNowWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		// 设置为0时0分0秒
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);

		return cal.getTime();
	}

	/**
	 * 获取月的第一天0点时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 当前时间是本月的第几天
	 *
	 * @param needDate :yyyy-MM-dd
	 * @return
	 */
	public static int getDayOfMonth(String needDate) {
		Date date = null;
		// 得到日历的实例
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isBlank(needDate)) {
			date = new Date();
		} else {
			try {
				date = DateUtilsPlus.parseDate(needDate, DATE_NORMAL_STYLE);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (date != null) {
			// 将现在的时间赋值给Calendar对象
			calendar.setTime(date);
		}

		// 取得日期
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前天数的第num天
	 * 
	 * @param date :yyyy-MM-dd
	 *
	 * @param num:大于0的数字
	 * @return
	 */
	public static String getBeforeDay(String date, int num) {
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isNotBlank(date)) {
			Date time = null;
			try {
				time = DateUtilsPlus.parseDate(date, DATE_NORMAL_STYLE);
				calendar.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		calendar.add(Calendar.DATE, -num);
		return DateFormatUtils.format(calendar, DATE_NORMAL_STYLE);
	}

	/**
	 * 获取上个月的最后一天
	 *
	 * @return
	 */
	public static String getLastDayOfBeforeMonth() {
		SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_NORMAL_STYLE);
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date strDateTo = calendar.getTime();
		return sdf1.format(strDateTo);
	}


}


