package com.foodquest.utils;

import java.text.DecimalFormat;

/**
* CommonUtil contains common utility functions
*
* @author  Shreyas K
*/
public class CommonUtil {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static Long getCurrentTimeInMillis() {
		return System.currentTimeMillis();
	}

	public static String generateRandomAlphanumeric(int length) {
		StringBuilder builder = new StringBuilder();

		while (length-- != 0) {
			int charPos = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(charPos));
		}
		return builder.toString();
	}
	
	public static double formatDecimalValue(double value) {
		 DecimalFormat f = new DecimalFormat("##.00");
		 return Double.valueOf(f.format(value));
	}
}
