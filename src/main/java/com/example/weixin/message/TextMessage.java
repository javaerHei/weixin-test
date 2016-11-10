package com.example.weixin.message;

/**
 * 文本消息
 * <br>创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class TextMessage extends BaseMessage {

	private String Content;// 	文本消息内容

	public TextMessage() {
		super(EMessageType.TEXT.getValue());
	}

	public TextMessage(String toUserName, String fromUserName,String content) {
		super(toUserName, fromUserName);
		setMsgType(EMessageType.TEXT.getValue());
		Content = content;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		Content = content;
	}
	
	
}
