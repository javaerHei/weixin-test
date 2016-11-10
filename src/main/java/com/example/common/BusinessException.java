package com.example.common;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -1956089458134530038L;

	private String errorCode = ErrorCode.SYSTEM_ERROR;
	private Boolean success;

	public BusinessException() {
		super();
	}

	public BusinessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}


	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String errorCode, Object message) {
		super(message.toString());
		this.errorCode = errorCode;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Boolean getSuccess() {
		return success;
	}

}
