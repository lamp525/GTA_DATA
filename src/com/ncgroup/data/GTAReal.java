package com.ncgroup.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

import com.ncgroup.util.DateHelper;

/**
 * @description:
 * @author: Lin
 * @create: 2018年10月23日 上午9:05:05
 * @version: 1.0
 */
public class GTAReal {

	/**
	 * @description:
	 * 
	 */
	public GTAReal() {

	}

	public ResultSet get1MKD_Real(Long timeStamp) {

		String tblName = getTableName(null);
		StringBuilder sqlCmd = new StringBuilder();
		sqlCmd.append(" SELECT  * FROM 2_1kmd. "+tblName);

		return null;
	}

	private String getTableName(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null)
			calendar.setTime(date);
		return (String.valueOf(calendar.get(Calendar.YEAR)) + '_' + String.valueOf(calendar.get(Calendar.MONTH)) + '_'
				+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
	}
}
