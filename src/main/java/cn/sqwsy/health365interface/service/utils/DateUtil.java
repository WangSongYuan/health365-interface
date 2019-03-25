package cn.sqwsy.health365interface.service.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 功能：时间处理工具类
 * Version 0.1
 */
public class DateUtil {
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.CHINESE);
		return df.format(date);
	}
	
	/**
	 * 解析时间字符串
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date parse(String dateString,String format){
		return parse(dateString,format,Locale.CHINESE,TimeZone.getDefault());
	}
	
	/**
	 * 解析时间字符串
	 * @param dateString
	 * @param format
	 * @param locale
	 * @param timeZone
	 * @return
	 */
	public static Date parse(String dateString,String format,Locale locale,TimeZone timeZone){
		SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG, locale);
        formatter.applyPattern(format);
        formatter.setTimeZone(timeZone);
		Date date = null;
		try{
			date = formatter.parse(dateString);
		}catch(Exception e){
			date = null;
		}
		return date;
	}
 }