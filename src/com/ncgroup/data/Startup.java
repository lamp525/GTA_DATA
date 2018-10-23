package com.ncgroup.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ncgroup.util.DateHelper;
import com.ncgroup.util.Log;
import com.ncgroup.util.SqlHelper;

/**
 * @description:
 * @author: Lin
 * @create: 2018年10月19日 下午4:08:25
 * @version: 1.0
 */
public class Startup {
	/**
	 * @description:
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			

			
			String sql1 = " SELECT close, utime, from_unixtime(utime,'%Y%m%d %k%i%s') as tt FROM 2_1mkd.2018_10_18  where symbol = '000001' ";
			String sql = " SELECT *  FROM CTMDB.DBO.USERINFO ";
			
			System.out.println(sql);
			
			Long start = System.currentTimeMillis();
			Connection conn = SqlHelper.getConnection(SqlHelper.SRV_3102);
			ResultSet rs = SqlHelper.getResultSet(conn,sql);

			if (rs != null && rs.first()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
			}
			Log.info("test");
			System.out.println(System.currentTimeMillis() - start);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
