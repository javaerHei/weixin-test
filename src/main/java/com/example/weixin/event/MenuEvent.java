package com.example.weixin.event;

import com.example.weixin.message.BaseMessage;
import com.example.weixin.message.EMessageType;

/**
 * 自定义菜单事件
 * <br>创建日期：2016年11月10日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class MenuEvent extends BaseMessage {

	private String Event;//事件类型
	private String EventKey;//事件KEY值，

	public MenuEvent() {
		super(EMessageType.EVENT.getValue());
	}

	public MenuEvent(String event, String eventKey) {
		this();
		Event = event;
		EventKey = eventKey;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
	
	
}
