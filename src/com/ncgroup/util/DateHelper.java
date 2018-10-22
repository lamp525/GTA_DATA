package com.ncgroup.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: ���ڴ�����
 * @author: Lin
 * @created: 2018��9��13�� ����4:45:11
 * @version: 1.0
 */
public class DateHelper {

	/**
	 * @description: ��ʽ��ʱ��
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatTime(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * @description: ��ȡ��ǰʱ�� ��Ĭ�ϸ�ʽ "yyyy-MM-dd HH:mm:ss"��
	 * @return
	 */
	public static String now() {
		return now("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @description: ��ȡ��ǰʱ��
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern) {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * @description: �жϵ�ǰʱ���Ƿ�Ϊ������
	 * @return
	 */
	public static Boolean isTradingDay() {
		int wd = Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
		if (wd == 1 || wd == 7)
			return false;
		else
			return true;
	}

	/**
	 * @description: ��ȡ��ǰʱ�������
	 * @return
	 */
	public static Integer getWeekDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * @description: ��ȡ��ǰʱ�����
	 * @return
	 */
	public static Integer getYearByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * @description: ��ȡ��ǰʱ�����
	 * @return
	 */
	public static Integer getMonthByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH)) + 1;
	}

	/**
	 * @description: ��ȡ��ǰʱ�����
	 * @return
	 */
	public static Integer getDayByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @description: ��ȡ��ǰʱ���Сʱ
	 * @return
	 */
	public static Integer getHourByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * ��ȡ��ǰʱ���Сʱ
	 * 
	 * @param date
	 */
	public static Integer getHourByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * ��ȡ��ǰʱ��ķ���
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Integer.valueOf(calendar.get(Calendar.MINUTE));
	}

	/**
	 * ��ȡ��ǰʱ��ķ���
	 * 
	 * @param date
	 */
	public static Integer getMinuteByCurrentTime() {
		return Integer.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮����
	 * @param year
	 * @return
	 */
	public static String getTimeByYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮����
	 * @param month
	 * @return
	 */
	public static String getTimeByMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮����
	 * @param day
	 * @return
	 */
	public static String getTimeByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮��Сʱ
	 * @param hour
	 * @return
	 */
	public static String getTimeByHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮�󼸷���
	 * @param date
	 * @param minute
	 * @param pattern
	 * @return
	 */
	public static String getTimeByMinute(Date date, int minute, String pattern) {
		if ((pattern == null) || (pattern.length() == 0)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮�󼸷���
	 * @param minute
	 * @return
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * @description: ��ȡָ��HH:ssʱ��֮ǰ��֮�󼸷���
	 * @param curTime
	 * @param minute
	 * @return
	 */
	public static String getTimeByMinute(String curTime, int minute) {
		if (curTime != null && curTime.length() == 5) {
			Calendar calendar = Calendar.getInstance();
			int curHour = Integer.parseInt(curTime.split(":")[0].trim());
			int curMin = Integer.parseInt(curTime.split(":")[1].trim());
			calendar.set(Calendar.HOUR_OF_DAY, curHour);
			calendar.set(Calendar.MINUTE, curMin);
			calendar.add(Calendar.MINUTE, minute);

			return new SimpleDateFormat("HH:mm").format(calendar.getTime());
		} else
			return "";
	}

	/**
	 * @description: ��ȡ��ǰʱ��֮ǰ��֮����
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getTimeBySecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * @description: ����ת��Ϊ �졢Сʱ�����ӡ��롢����
	 * @param ms
	 * @return
	 */
	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day; // ��
		String strHour = hour < 10 ? "0" + hour : "" + hour;// Сʱ
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// ����
		String strSecond = second < 10 ? "0" + second : "" + second;// ��
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// ����
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strDay + "��" + strHour + "Сʱ" + strMinute + "����" + strSecond + "��" + strMilliSecond + "����";
	}

}
