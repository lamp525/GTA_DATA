package com.ncgroup.util;

import java.sql.*;
import java.util.logging.*;

/**
 * @description: SQL 基本操作 通过它,可以很轻松的使用 JDBC 来操纵数据库
 * @author: Lin
 * @created: 2018年9月13日 下午4:50:17
 * @version: 1.0
 */
public class SqlHelper {

	/**
	 * MSSQL 驱动
	 */
	private static final String msSqlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/**
	 * MYSQL 驱动
	 */
	private static final String mySqlDriver = "com.mysql.cj.jdbc.Driver";

	/**
	 * MSSQL 连接字符串
	 */
	private static final String msSqlUrl = "jdbc:sqlserver://%s;databaseName=%s";

	/**
	 * MYSQL 连接字符串
	 */
	private static final String mySqlUrl = "jdbc:mysql://%s:3306/%s?serverTimezone=UTC&characterEncoding=utf-8  ";

	/**
	 * MSSQL 10.10.16.77 GTA_DATA
	 */
	public static DbParam GTA_DATA = new DbParam("mssql", "10.10.16.77", "GTA_DATA", "sa", "Nc@123");

	/**
	 * MSSQL 10.10.10.2 CTMDB
	 */
	public static DbParam SRV_3102 = new DbParam("mssql", "10.10.10.2\\ZB", "CTMDB", "sa", "Nc@3102");

	/**
	 * MSSQL 10.10.10.8 NC_MIN01
	 */
	public static DbParam SRV_3108 = new DbParam("mssql", "10.10.10.8", "NC_MIN01", "LH002", "Nc1002");

	/**
	 * MSSQL 10.10.10.10 GTA_NXTZ
	 */
	public static DbParam GTA_HIS = new DbParam("mssql", "10.10.10.10", "GTA_NXTZ", "read", "nc_123456");

	/**
	 * MYSQL 10.10.10.104 nc_1mstd
	 */
	public static DbParam GTA_REAL = new DbParam("mysql", "10.10.10.104", "nc_1mstd", "root", "123456");

	/**
	 * @description: 不允许实例化该类
	 *
	 */
	private SqlHelper() {
	}

	/**
	 * @description: 获取一个数据库连接
	 * @param param
	 * @return 数据库连接
	 */
	public static Connection getConnection(DbParam param) {
		String url = null;
		try {
			if ("mssql".equals(param.getServerType().toLowerCase())) {
				url = String.format(msSqlUrl, param.getIp(), param.getDbName());
				Class.forName(msSqlDriver);
			} else if ("mysql".equals(param.getServerType().toLowerCase())) {
				url = String.format(mySqlUrl, param.getIp(), param.getDbName());
				Class.forName(mySqlDriver);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return DriverManager.getConnection(url, param.getUser(), param.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一个 Statement 该 Statement 已经设置数据集 可以滚动,可以更新
	 * 
	 * @param conn 数据库连接
	 * @return 如果获取失败将返回 null,调用时记得检查返回值
	 */
	public static Statement getStatement(Connection conn) {
		if (conn == null) {
			return null;
		}
		try {

			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// 设置数据集可以滚动,可以更新
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * 获取一个带参数的 PreparedStatement 该 PreparedStatement 已经设置数据集 可以滚动,可以更新
	 * 
	 * @param conn      数据库连接
	 * @param cmdText   需要 ? 参数的 SQL 语句
	 * @param cmdParams SQL 语句的参数表
	 * @return 如果获取失败将返回 null,调用时记得检查返回值
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String cmdText, Object... cmdParams) {
		if (conn == null) {
			return null;
		}

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(cmdText, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int i = 1;
			for (Object item : cmdParams) {
				pstmt.setObject(i, item);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			close(pstmt);
		}
		return pstmt;
	}

	/**
	 * 执行 SQL 语句,返回结果为整型 主要用于执行非查询语句
	 * 
	 * @param cmdText SQL 语句
	 * @return 非负数:正常执行; -1:执行错误; -2:连接错误
	 */
	public static int execSql(Connection conn, String cmdText) {
		Statement stmt = getStatement(conn);
		if (stmt == null) {
			return -2;
		}
		int i;
		try {
			i = stmt.executeUpdate(cmdText);

		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			i = -1;
		}
		close(stmt);
		return i;
	}

	/**
	 * 执行 SQL 语句,返回结果为整型 主要用于执行非查询语句
	 * 
	 * @param conn      数据库连接
	 * @param cmdText   需要 ? 参数的 SQL 语句
	 * @param cmdParams SQL 语句的参数表
	 * @return 非负数:正常执行; -1:执行错误; -2:连接错误
	 */
	public static int execSql(Connection conn, String cmdText, Object... cmdParams) {
		PreparedStatement pstmt = getPreparedStatement(conn, cmdText, cmdParams);
		if (pstmt == null) {
			return -2;
		}
		int i;
		try {
			i = pstmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			i = -1;
		}
		close(pstmt);
		return i;
	}

	/**
	 * 返回结果集的第一行的一列的值,其他忽略
	 * 
	 * @param conn    数据库连接
	 * @param cmdText SQL 语句
	 * @return
	 */
	public static Object ExecScalar(Connection conn, String cmdText) {
		ResultSet rs = getResultSet(conn, cmdText);
		Object obj = buildScalar(rs);
		closeEx(rs);
		return obj;
	}

	/**
	 * 返回结果集的第一行的一列的值,其他忽略
	 * 
	 * @param conn      数据库连接
	 * @param cmdText   需要 ? 参数的 SQL 语句
	 * @param cmdParams SQL 语句的参数表
	 * @return
	 */
	public static Object ExecScalar(Connection conn, String cmdText, Object... cmdParams) {
		ResultSet rs = getResultSet(conn, cmdText, cmdParams);
		Object obj = buildScalar(rs);
		closeEx(rs);
		return obj;
	}

	/**
	 * 返回一个 ResultSet
	 * 
	 * @param conn
	 * @param cmdText SQL 语句
	 * @return
	 */
	public static ResultSet getResultSet(Connection conn, String cmdText) {
		Statement stmt = getStatement(conn);
		if (stmt == null) {
			return null;
		}
		try {
			return stmt.executeQuery(cmdText);
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			close(stmt);
		}
		return null;
	}

	/**
	 * 返回一个 ResultSet
	 * 
	 * @param conn      数据库连接
	 * @param cmdText   需要 ? 参数的 SQL 语句
	 * @param cmdParams SQL 语句的参数表
	 * @return
	 */
	public static ResultSet getResultSet(Connection conn, String cmdText, Object... cmdParams) {
		PreparedStatement pstmt = getPreparedStatement(conn, cmdText, cmdParams);
		if (pstmt == null) {
			return null;
		}
		try {
			return pstmt.executeQuery();
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			close(pstmt);
		}
		return null;
	}

	public static Object buildScalar(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		Object obj = null;
		try {
			if (rs.next()) {
				obj = rs.getObject(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return obj;
	}

	private static void close(Object obj) {
		if (obj == null) {
			return;
		}
		try {
			if (obj instanceof Statement) {
				((Statement) obj).close();
			} else if (obj instanceof PreparedStatement) {
				((PreparedStatement) obj).close();
			} else if (obj instanceof ResultSet) {
				((ResultSet) obj).close();
			} else if (obj instanceof Connection) {
				((Connection) obj).close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void closeEx(Object obj) {
		if (obj == null) {
			return;
		}

		try {
			if (obj instanceof Statement) {
				((Statement) obj).close();
			} else if (obj instanceof PreparedStatement) {
				((PreparedStatement) obj).close();
			} else if (obj instanceof ResultSet) {
				((ResultSet) obj).getStatement().close();
			} else if (obj instanceof Connection) {
				((Connection) obj).close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private static void closeConnection(Object obj) {
		if (obj == null) {
			return;
		}
		try {
			if (obj instanceof Statement) {
				((Statement) obj).getConnection().close();
			} else if (obj instanceof PreparedStatement) {
				((PreparedStatement) obj).getConnection().close();
			} else if (obj instanceof ResultSet) {
				((ResultSet) obj).getStatement().getConnection().close();
			} else if (obj instanceof Connection) {
				((Connection) obj).close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}