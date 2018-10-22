package com.ncgroup.data;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ncgroup.util.SqlHelper;

/**
 * @description:
 * @author: Lin
 * @create: 2018年10月19日 下午5:24:41
 * @version: 1.0
 */
public class DataSource {

	/**
	 * @description:
	 * 
	 */
	public DataSource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description:取得1分钟标准值
	 *
	 */
	public ResultSet getStdVolMin01(Date date) {
		ResultSet rs = null;
		try {
//			String sql = "SELECT StockCode,Market,TradeTime,Volume FROM STD_MIN01 WHERE TradeDate = '"
//					+ DateHelper.getFormatTime(date, "yyyy-MM-dd") + "'";
//			rs = SqlHelper.getResultSet(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * @description:取得上一交易日日期
	 * @return
	 */
	public Date getPreTradingDay() {
		Date date = null;
//		try {
//			String sql = "SELECT MAX(TradeDate) FROM STD_MIN01 ";
//			Object obj = SqlHelper.ExecScalar(sql);
//			date = obj == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(obj.toString());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		return date;
	}
	
	/**
	 * @description: 取的实时一分钟K线数据
	 * @return
	 */
	public ResultSet getKLineMin01_Real()
	{
		//String sql = "SELECT ";
		 
		 return null;
	}

}
