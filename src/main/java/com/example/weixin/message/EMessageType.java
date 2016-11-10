package com.example.weixin.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;

/**
 * 微信消息类型 <br>
 * 创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public enum EMessageType {

	TEXT("文本消息", "text"), 
	IMAGE("图片消息", "image"), 
	VOICE("语音消息", "voice"), 
	VIDEO("视频消息", "video"), 
	SHORT_VIDEO("小视频消息","shortvideo"), 
	LOCATION("地理位置消息", "location"), 
	LINK("链接消息", "link"),
	EVENT("事件", "event");

	private String name;
	private String value;

	private EMessageType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	private static List<EMessageType> enums = EnumUtils.getEnumList(EMessageType.class);

	public static List<EMessageType> getAll() {
		List<EMessageType> list = new ArrayList<EMessageType>();
		for (EMessageType e : enums) {
			list.add(e);
		}
		return list;
	}

	public static boolean exists(String name) {
		for (EMessageType e : enums) {
			if (e.getName().equals(name))
				return true;
		}
		return false;
	}

	public static EMessageType value(String value) {
		for (EMessageType e : enums) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		return enums.get(0);
	}

}
