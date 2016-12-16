package com.foodquest.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonDBUtil {
	public static boolean findIfColumnExists(String columnName, ResultSet rs) {
		try {
			rs.findColumn(columnName);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
