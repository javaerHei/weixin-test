package com.example.common;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpRetryHelp {
	private static final Logger logger = LoggerFactory.getLogger(HttpRetryHelp.class);

	// 重试
	/**
	 * @since 1.0 
	 * @param reTry reTry
	 * @param deplay deplay
	 * @param retryNum retryNum
	 * @return
	 */
	public static Object httpRetry(ReTry reTry, long deplay, int retryNum) {
		Object callback = -1;
		Integer invoker = 1;
		String errorMsg="";
		Boolean hasException = true;
		do {
			try {
				callback = reTry.exec();
				hasException = false;
				break;
			} catch (BusinessException e) {
				errorMsg=e.getMessage();
				throw new OnsRetryException(e.getMessage() + "，消费重试!");
			} catch (Exception e) {
				errorMsg=e.getMessage();
				logger.error(ExceptionUtils.getMessage(e));
				hasException = true;
				try {
					Thread.sleep(deplay);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			invoker++;
			logger.info("接口调用重试第{}次,hasException={}", invoker, hasException);
		} while (hasException && invoker < retryNum);
		if (hasException) {
			throw new BusinessException(errorMsg);
		}
		return callback;
	}

	// 重试
	/**
	 * @since 1.0 
	 * @param reTry reTry
	 * @return
	 */
	public static Object httpRetry(ReTry reTry) {
		return httpRetry(reTry, 1000, 30);
	}


	public static interface ReTry {
		Object exec();
	}
}
