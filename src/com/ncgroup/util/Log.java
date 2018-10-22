package com.ncgroup.util;

import org.apache.log4j.Logger;

/**
 * @description: ��־������
 * @author: Lin
 * @created: 2018��9��13�� ����4:46:56
 * @version: 1.0
 */
public class Log {

	private static final String PREFIX = ">> ";
	private static Logger _logger = Logger.getLogger(Log.class);

	/**
	 * @description: ������־
	 * @param message
	 */
	public static void debug(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.debug(message);
	}

	/**
	 * @description: ��Ϣ��־
	 * @param message
	 */
	public static void info(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.info(message);
	}

	/**
	 * @description: ������־
	 * @param message
	 */
	public static void warn(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.warn(message);
	}

	/**
	 * @description: ������־
	 * @param message
	 */
	public static void error(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.error(message);
	}

	/**
	 * @description: ������־
	 * @param message
	 * @param e
	 */
	public static void error(String message, Exception e) {
		message = getStackTrace() + PREFIX + message;
		_logger.error(message, e);
	}

	/**
	 * @description: ��ȡ�����̶߳�ջ������Ϣ
	 * @return
	 */
	private static String getStackTrace() {
		String location = "";
		try {
			StackTraceElement stack = Thread.currentThread().getStackTrace()[3];
			location = "[" + " Thread: " + Thread.currentThread().getName() + " Class: " + stack.getClassName()
					+ " Method: " + stack.getMethodName() + "(Line: " + stack.getLineNumber() + ")" + "]" + "\n";
		} catch (Exception e) {

		}
		return location;
	}
}
