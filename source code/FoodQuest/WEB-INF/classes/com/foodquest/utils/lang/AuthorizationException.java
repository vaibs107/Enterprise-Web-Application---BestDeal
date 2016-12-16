package com.foodquest.utils.lang;
/**
* AppException custom exception to represent authorization exceptions
*
* @author  Shreyas K
*/
public class AuthorizationException extends AppException {
	private static final long serialVersionUID = 13L;

	public AuthorizationException(Integer code, String message) {
		super(code, message);
	}

	public AuthorizationException(String message) {
		super(message);
	}

}
