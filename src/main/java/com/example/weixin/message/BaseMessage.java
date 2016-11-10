package com.example.weixin.message;

import java.util.Date;

/**
 * 微信消息base <br>
 * 创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class BaseMessage {

	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;// 消息创建时间 （整型）
	private String MsgType;
	private Long MsgId;// 消息id，64位整型

	public BaseMessage() {
		super();
		CreateTime = new Date().getTime();
	}

	public BaseMessage(String msgType) {
		this();
		MsgType = msgType;
	}

	public BaseMessage(String toUserName, String fromUserName) {
		this();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		
	}

	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return ToUserName;
	}

	/**
	 * @param toUserName
	 *            the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return FromUserName;
	}

	/**
	 * @param fromUserName
	 *            the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	/**
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return CreateTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return MsgType;
	}

	/**
	 * @param msgType
	 *            the msgType to set
	 */
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	/**
	 * @return the msgId
	 */
	public Long getMsgId() {
		return MsgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

}
