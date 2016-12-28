
package com.example.common;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回实体
 * @since 1.0
 * @version 1.0
 */
public class JsonResult {

	private boolean success;
	
	private String code = "";
	
	private String msg = "";
	
	private JSONObject data;
	
	/**
	 * 转换JSON
	 * @since 1.0
	 * @return
	 */
	public String toJson(){
		JSONObject result = new JSONObject();
		result.put("success", success);
		result.put("msg", msg);
		result.put("code", code);
		result.put("data", data);
		return result.toJSONString();
	}
	
	/**
	 * 
	 * @since 1.0 
	 * @return
	 */
	public JSONObject toJsonObject(){
		JSONObject result = new JSONObject();
		result.put("success", success);
		result.put("msg", msg);
		result.put("code", code);
		result.put("data", data);
		return result;
	}
	
	/**
	 * 添加data数据
	 * @since 1.0
	 * @param data
	 */
	public void addData(JSONObject data){
		this.data = data;
	}
	
	/**
	 * 添加data数据
	 * @since 1.0
	 * @param data
	 */
	public void addData(String data){
		this.data = JSONObject.parseObject(data);
	}

	/**
	 * 
	 * @param success success
	 * @param msg msg
	 */
	public JsonResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	/**
	 * 
	 * @param success success
	 */
	public JsonResult(boolean success) {
		super();
		this.success = success;
	}

	/**
	 * 
	 * @param success success
	 * @param code code
	 * @param msg msg
	 */
	public JsonResult(boolean success, String code, String msg) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
