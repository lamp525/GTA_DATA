package com.ncgroup.util;

/**
 * @description:
 * @author: Lin
 * @create: 2018年10月22日 下午3:12:58
 * @version: 1.0
 */

public class DbParam {

	private String _ip = null;
	private String _dbName = null;
	private String _user = null;
	private String _password = null;
	private String _serverType = null;

	/**
	 * @description:
	 * @param serverType
	 * @param dbName
	 * @param user
	 * @param password
	 */
	public DbParam(String serverType, String ip, String dbName, String user, String password) {
		this._serverType = serverType;
		this._ip = ip;
		this._dbName = dbName;
		this._user = user;
		this._password = password;
	}

	/**
	 * @return the _ip
	 */
	public String getIp() {
		return _ip;
	}

	/**
	 * @param _ip the _ip to set
	 */
	public void setIp(String _ip) {
		this._ip = _ip;
	}

	/**
	 * @return the _dbName
	 */
	public String getDbName() {
		return _dbName;
	}

	/**
	 * @param _dbName the _dbName to set
	 */
	public void setDbName(String _dbName) {
		this._dbName = _dbName;
	}

	public String getUser() {
		return this._user;
	}

	public void setUser(String user) {
		this._user = user;
	}

	public String getPassword() {
		return this._password;
	}

	public void setPassword(String password) {
		this._password = password;
	}

	/**
	 * @return the _serverType
	 */
	public String getServerType() {
		return _serverType;
	}

	/**
	 * @param _serverType the _serverType to set
	 */
	public void setServerType(String _serverType) {
		this._serverType = _serverType;
	}
}
