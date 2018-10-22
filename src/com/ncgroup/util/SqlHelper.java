package com.ncgroup.util;

import java.sql.*;
import java.util.logging.*;

/**
 * @description: SQL �������� ͨ����,���Ժ����ɵ�ʹ�� JDBC ���������ݿ�
 * @author: Lin
 * @created: 2018��9��13�� ����4:50:17
 * @version: 1.0
 */
public class SqlHelper {

	/**
	 * MSSQL ����
	 */
	private static final String msSqlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	/**
	 * MYSQL ����
	 */
	private static final String mySqlDriver = "com.mysql.cj.jdbc.Driver";

	/**
	 * MSSQL �����ַ���
	 */
	private static final String msSqlUrl = "jdbc:sqlserver://%s;databaseName=%s";

	/**
	 * MYSQL �����ַ���
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
	 * @description: ������ʵ��������
	 *
	 */
	private SqlHelper() {
	}

	/**
	 * @description: ��ȡһ�����ݿ�����
	 * @param param
	 * @return ���ݿ�����
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
	 * ��ȡһ�� Statement �� Statement �Ѿ��������ݼ� ���Թ���,���Ը���
	 * 
	 * @param conn ���ݿ�����
	 * @return �����ȡʧ�ܽ����� null,����ʱ�ǵü�鷵��ֵ
	 */
	public static Statement getStatement(Connection conn) {
		if (conn == null) {
			return null;
		}
		try {

			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// �������ݼ����Թ���,���Ը���
		} catch (SQLException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * ��ȡһ���������� PreparedStatement �� PreparedStatement �Ѿ��������ݼ� ���Թ���,���Ը���
	 * 
	 * @param conn      ���ݿ�����
	 * @param cmdText   ��Ҫ ? ������ SQL ���
	 * @param cmdParams SQL ���Ĳ�����
	 * @return �����ȡʧ�ܽ����� null,����ʱ�ǵü�鷵��ֵ
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
	 * ִ�� SQL ���,���ؽ��Ϊ���� ��Ҫ����ִ�зǲ�ѯ���
	 * 
	 * @param cmdText SQL ���
	 * @return �Ǹ���:����ִ��; -1:ִ�д���; -2:���Ӵ���
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
	 * ִ�� SQL ���,���ؽ��Ϊ���� ��Ҫ����ִ�зǲ�ѯ���
	 * 
	 * @param conn      ���ݿ�����
	 * @param cmdText   ��Ҫ ? ������ SQL ���
	 * @param cmdParams SQL ���Ĳ�����
	 * @return �Ǹ���:����ִ��; -1:ִ�д���; -2:���Ӵ���
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
	 * ���ؽ�����ĵ�һ�е�һ�е�ֵ,��������
	 * 
	 * @param conn    ���ݿ�����
	 * @param cmdText SQL ���
	 * @return
	 */
	public static Object ExecScalar(Connection conn, String cmdText) {
		ResultSet rs = getResultSet(conn, cmdText);
		Object obj = buildScalar(rs);
		closeEx(rs);
		return obj;
	}

	/**
	 * ���ؽ�����ĵ�һ�е�һ�е�ֵ,��������
	 * 
	 * @param conn      ���ݿ�����
	 * @param cmdText   ��Ҫ ? ������ SQL ���
	 * @param cmdParams SQL ���Ĳ�����
	 * @return
	 */
	public static Object ExecScalar(Connection conn, String cmdText, Object... cmdParams) {
		ResultSet rs = getResultSet(conn, cmdText, cmdParams);
		Object obj = buildScalar(rs);
		closeEx(rs);
		return obj;
	}

	/**
	 * ����һ�� ResultSet
	 * 
	 * @param conn
	 * @param cmdText SQL ���
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
	 * ����һ�� ResultSet
	 * 
	 * @param conn      ���ݿ�����
	 * @param cmdText   ��Ҫ ? ������ SQL ���
	 * @param cmdParams SQL ���Ĳ�����
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