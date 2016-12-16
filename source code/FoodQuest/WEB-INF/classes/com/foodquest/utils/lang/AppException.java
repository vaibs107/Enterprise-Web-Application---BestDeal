package com.foodquest.utils.lang;
/**
* AppException custom exception to represent application level exceptions
*
* @author  Shreyas K
*/
public class AppException extends Exception {
	private static final long serialVersionUID = 12L;
	private Integer code;
	private String message;

	public AppException(String message) {
		this.message = message;
	}

	public AppException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppException [ ");
		if (code != null) {
			builder.append("Code = " + code + " ,");
		}
		if (message != null) {
			builder.append("Message " + message);
		}
		return builder.toString();
	}
}
