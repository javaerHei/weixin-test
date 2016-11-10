package com.example.weixin.menu;

import java.util.List;

/**
 * 微信自定义菜单
 * <br>创建日期：2016年11月10日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class Menu {
	
	private String type;//菜单的响应动作类型
	private String name;//菜单标题
	private String key;// 菜单KEY值，用于消息接口推送
	private String url;// 网页链接，用户点击菜单可打开链接
	private String media_id;// 调用新增永久素材接口返回的合法media_id
	
	private List<Menu> sub_button;
	
	public Menu() {
		super();
	}

	public Menu(String type, String name, String key, String url) {
		super();
		this.type = type;
		this.name = name;
		this.key = key;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public List<Menu> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Menu> sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
