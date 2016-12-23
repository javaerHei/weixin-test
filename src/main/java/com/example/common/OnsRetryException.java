package com.example.common;

import org.apache.commons.lang3.StringUtils;


public class OnsRetryException extends RuntimeException {

	private static final long serialVersionUID = -1956089458134530038L;
	private String errorCode;

	/**
	 * 
	 */
	public OnsRetryException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param errorCode 错误代码
	 * 
	 * @param message 错误消息
	 */
	public OnsRetryException(String errorCode, String message){
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param message 错误消息
	 * 
	 * @param cause cause
	 */
	public OnsRetryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message 错误消息
	 */
	public OnsRetryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause cause
	 */
	public OnsRetryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if(StringUtils.isNotBlank(message)){
			return message;
		}
		if(StringUtils.isNotBlank(this.errorCode)){
			return ErrorCode.getErrorMessage(this.errorCode);
		}
		return "";
	}
	
}

