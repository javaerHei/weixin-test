package com.example.common;

import com.alibaba.fastjson.JSONObject;

/**
 * json工具类
 *
 */
public class JsonUtils {
	
	/**错误Key*/
	private static final String ERROR_KEY = "errorMsg";
	/**错误Key*/
	private static final String ERROR_MSG_KEY = "msg";
	
	/**成功Key*/
	private static final String SUCCESS_KEY = "success"; 
	
	/**错误编码Key*/
	private static final String ERROR_CODE_KEY = "errorCode";

	/**
	 * 返回系统成功JSON
	 * @return String
	 */
	public static String getSuccess(){
		return result(true, "", "");
	}
	
	/**
	 * 返回系统成功JSONObject
	 * @return JSONObject
	 */
	public static JSONObject getSuccessJson(){
		return JSONObject.parseObject(getSuccess());
	}
	
	/**
	 * 返回带success的JsonObject
	 * @return JSONObject
	 */
	public static JSONObject getSimpleSuccess(){
		JSONObject json = new JSONObject();
		json.put(SUCCESS_KEY, true);
		return json;
	}
	
	/**
	 * 返回系统失败JSON
	 * @param errorMsg errorMsg
	 * @param errorCode  errorCode
	 * @return String
	 */
	public static String getFailure(String errorMsg, String errorCode){
		return result(false, errorMsg, errorCode);
	}
	
	/**
	 * @since 1.0 
	 * @param errorMsg errorMsg
	 * @return
	 * <br><b>作者： @author songxuan</b>
	 * <br>创建时间：2016年2月18日 上午10:57:50
	 */
	public static String getFailure(String errorMsg){
		return result(false, errorMsg, "");
	}
	

	/**
	 * 返回系统成功JSONObject
	 * @param errorMsg  errorMsg
	 * @param errorCode  errorCode
	 * @return JSONObject
	 */
	public static JSONObject getFailureJson(String errorMsg, String errorCode){
		return JSONObject.parseObject(getFailure(errorMsg, errorCode));
	}
	
	/**
	 * 封装通用JSON返回结果
	 * @param success success
	 * @param errorMsg errorMsg
	 * @param errorCode  errorCode
	 * @return String
	 */
	public static String result(boolean success, String errorMsg, String errorCode){
		JSONObject result = new JSONObject();
		result.put(SUCCESS_KEY, success);
		result.put(ERROR_KEY, errorMsg);
		result.put(ERROR_MSG_KEY, errorMsg);
		result.put(ERROR_CODE_KEY, errorCode);
		return result.toString();
	}
	
	/**
	 * @since 1.0 
	 * @param text text
	 * @return
	 * <br><b>作者： @author songxuan</b>
	 * <br>创建时间：2016年2月18日 上午10:58:27
	 */
	public static Boolean isJson(String text){
		try {
			JSONObject.parseObject(text);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
