package com.foodquest.models.db;

import com.foodquest.utils.PropertiesStore;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;

public enum MongoDBConnectionManager {
	INSTANCE;

	private String mongoHost;
	private String userName;
	private String password;
	private String dbName;
	private boolean autoConnect = true;
	private int connectionsPerHost = 100;
	private Mongo mongo;
	private DB appDB;

	private MongoDBConnectionManager() {
		initConfig();

		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(connectionsPerHost)
				.readPreference(ReadPreference.secondaryPreferred()).build();
		MongoClientURI u = new MongoClientURI(mongoHost);
		mongo = new MongoClient();
		appDB = mongo.getDB(dbName);
	}

	public DB getAppDB() {
		return appDB;
	}

	public void close() {
		mongo.close();
	}

	private void initConfig() {
		mongoHost = PropertiesStore.getInstance().getProperty("mongodb_host");
		userName = PropertiesStore.getInstance().getProperty("mongodb_username");
		password = PropertiesStore.getInstance().getProperty("mongodb_password");
		dbName = PropertiesStore.getInstance().getProperty("mongodb_dbName");
		autoConnect = Boolean.valueOf(PropertiesStore.getInstance().getProperty("autoConnect"));
		connectionsPerHost = Integer
				.valueOf(PropertiesStore.getInstance().getProperty("mongodb_connections_per_host").trim());
	}
	
	public static void main(String[] args) {
		System.out.println(MongoDBConnectionManager.INSTANCE.getAppDB());
	}
	
}
