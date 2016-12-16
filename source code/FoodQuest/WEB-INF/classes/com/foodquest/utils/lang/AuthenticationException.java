package com.foodquest.utils.lang;
/**
* AppException custom exception to represent authentication exceptions
*
* @author  Shreyas K
*/
public class AuthenticationException extends AppException {
	private static final long serialVersionUID = 14L;

	public AuthenticationException(Integer code, String message) {
		super(code, message);
	}

	public AuthenticationException(String message) {
		super(message);
	}

}
