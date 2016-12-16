package com.foodquest.models.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.foodquest.utils.PropertiesStore;

public class DBConnectionPoolManager {
	private static final Logger LOGGER = Logger.getLogger(DBConnectionPoolManager.class.getName());
	private static DBConnectionPoolManager INSTANCE = null;
	private String driver;
	private String url;
	private String user;
	private String password;
	private String dbName;
	private Integer poolSize;
	private List<Connection> connectionPool;

	private DBConnectionPoolManager() {
		try {
			initConfig();
			connectionPool = new ArrayList<Connection>(poolSize);
			Class.forName(driver);
			populateConnectionPool();
		} catch (Exception e) {

		}
	}

	public synchronized Connection getConnectionFromPool() {
		Connection conn = null;
		if (connectionPool.size() > 0) {
			conn = (Connection) connectionPool.get(0);
			connectionPool.remove(0);
		}
		LOGGER.info("Retrieved connection from pool. Current pool size " + getCurrentPoolSize());
		return conn;
	}

	public synchronized void returnConnectionToPool(Connection conn) {
		if (conn != null && !isConnectionPoolFull() && getCurrentPoolSize() <= poolSize) {
			connectionPool.add(conn);
			LOGGER.info("Added connection back to pool. Current pool size " + getCurrentPoolSize());
		}
	}

	public synchronized void clearConnectionPool() {
		LOGGER.info("Clearing connection pool");
		for (Connection conn : connectionPool) {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connectionPool.clear();
	}

	private Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url + "" + dbName, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void populateConnectionPool() {
		LOGGER.info("Creating db connection pool of size " + poolSize);
		while (!isConnectionPoolFull()) {
			Connection conn = createConnection();
			connectionPool.add(conn);
		}
	}

	private boolean isConnectionPoolFull() {
		return connectionPool.size() == poolSize;
	}

	private int getCurrentPoolSize() {
		return connectionPool.size();
	}

	private void initConfig() {
		driver = PropertiesStore.getInstance().getProperty("mysql_db_driver");
		url = PropertiesStore.getInstance().getProperty("mysql_db_url");
		user = PropertiesStore.getInstance().getProperty("mysql_db_username");
		password = PropertiesStore.getInstance().getProperty("mysql_db_password");
		dbName = PropertiesStore.getInstance().getProperty("mysql_db_dbName");
		poolSize = Integer.parseInt(PropertiesStore.getInstance().getProperty("mysql_connection_pool_size"));
	}

	public static DBConnectionPoolManager getInstance() {
		if (INSTANCE == null) {
			synchronized (DBConnectionPoolManager.class) {
				INSTANCE = new DBConnectionPoolManager();
			}
		}
		return INSTANCE;
	}
}
