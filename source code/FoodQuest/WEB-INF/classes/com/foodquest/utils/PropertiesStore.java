package com.foodquest.utils;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertiesStore {
	private final Properties configProp = new Properties();

	private PropertiesStore() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("app.properties");
		try {
			configProp.load(in);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static class LazyHolder {
		private static final PropertiesStore INSTANCE = new PropertiesStore();
	}

	public static PropertiesStore getInstance() {
		return LazyHolder.INSTANCE;
	}

	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}
}
