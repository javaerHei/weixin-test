package com.example.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 系统错误编码定义
 * 
 *
 */
public class ErrorCode {

	/** 系统错误 */
	public static final String SYSTEM_ERROR = "100000";

	/** 参数错误 */
	public static final String PARAM_ERROR = "100001";

	/** 记录已经存在 */
	public static final String RECORD_EXSIT_ERROR = "100005";

	/** 参数超过最大限制 */
	public static final String PARAM_TO_LONG_ERROR = "100010";

	/** 记录不存在 */
	public static final String RECORD_NOT_EXSIT_ERROR = "100015";

	/** 参数不能为空 */
	public static final String PARAM_NULL_ERROR = "100020";

	/** 权限不足 */
	public static final String PERMISSION_DENIED_ERROR = "100025";

	/** 对象不能修改 */
	public static final String UNABLE_MODIFY_ERROR = "100030";

	/** 错误消息容器 */
	private static final Map<String, String> ERROR_MESSAGE_MAP = new HashMap<String, String>();

	static {
		ERROR_MESSAGE_MAP.put(SYSTEM_ERROR, "系统出现异常");
		ERROR_MESSAGE_MAP.put(PARAM_ERROR, "请检查参数是否正确");
		ERROR_MESSAGE_MAP.put(PARAM_TO_LONG_ERROR, "记录已经存在");
		ERROR_MESSAGE_MAP.put(PARAM_TO_LONG_ERROR, "参数超出最大长度限制");
		ERROR_MESSAGE_MAP.put(RECORD_NOT_EXSIT_ERROR, "记录不存在");
		ERROR_MESSAGE_MAP.put(PARAM_NULL_ERROR, "请求参数不能为空");
		ERROR_MESSAGE_MAP.put(PERMISSION_DENIED_ERROR, "权限不足");
		ERROR_MESSAGE_MAP.put(UNABLE_MODIFY_ERROR, "对象不能修改");
	}

	/**
	 * 
	 * @param errorCode 错误代码
	 * @return <br>
	 *         <b>作者： @author Administrator</b> <br>
	 *         创建时间：2015年5月15日 上午10:00:21
	 */
	public static String getErrorMessage(String errorCode) {
		if (ERROR_MESSAGE_MAP.containsKey(errorCode)) {
			return ERROR_MESSAGE_MAP.get(errorCode);
		}
		return "系统维护中";
	}
}
