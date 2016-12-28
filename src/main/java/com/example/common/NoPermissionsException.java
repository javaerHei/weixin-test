package com.example.common;

/**
 * <br>
 * 
 * @since 1.0
 * @version 1.0
 */
public class NoPermissionsException extends RuntimeException {

	private static final long serialVersionUID = -1956089458134530038L;

	private String errorCode;

	/**
	 * 
	 */
	public NoPermissionsException() {
		super();
	}

	/**
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            信息
	 */
	public NoPermissionsException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 *            信息
	 * @param cause
	 *            异常
	 */
	public NoPermissionsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 *            信息
	 */
	public NoPermissionsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            异常
	 */
	public NoPermissionsException(Throwable cause) {
		super(cause);
	}


	public String getErrorCode() {
		return errorCode;
	}

}
