package com.example.common;


/**
 * 会话超时异常
 * 
 * @since 1.0
 * @version 1.0
 */
public class SessionTimeoutException extends BusinessException {

	private static final long serialVersionUID = -2099663546472519958L;

	/**
     * 
     */
	public SessionTimeoutException() {
		super();
	}

	/**
	 * @param errorCode 错误代码
	 * @param message 信息
	 */
	public SessionTimeoutException(String errorCode, String message) {
		super(errorCode, message);
	}

	/**
	 * @param message 信息
	 * @param cause 异常
	 */
	public SessionTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message 信息
	 */
	public SessionTimeoutException(String message) {
		super(message);
	}

	/**
	 * @param cause 异常
	 */
	public SessionTimeoutException(Throwable cause) {
		super(cause);
	}

}
