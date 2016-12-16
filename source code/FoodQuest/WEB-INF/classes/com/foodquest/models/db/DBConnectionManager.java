package com.foodquest.models.db;

import java.sql.Connection;

public class DBConnectionManager {
	private static DBConnectionManager INSTANCE = null;
	private DBConnectionPoolManager connectionPoolManager;

	private DBConnectionManager() {
		connectionPoolManager = DBConnectionPoolManager.getInstance();
	}

	public Connection getConnection() {
		return connectionPoolManager.getConnectionFromPool();
	}

	public void returnConnection(Connection conn) {
		connectionPoolManager.returnConnectionToPool(conn);
	}

	public void closeConnections() {
		connectionPoolManager.clearConnectionPool();
	}

	public static DBConnectionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (DBConnectionManager.class) {
				INSTANCE = new DBConnectionManager();
			}
		}
		return INSTANCE;
	}
}
