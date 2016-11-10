package com.example.weixin.message;

/**
 * 链接消息 <br>
 * 创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class LinkMessage extends BaseMessage {

	private String Title;// 消息标题
	private String Description;// 消息描述
	private String Url;// 消息链接

	public LinkMessage() {
		super(EMessageType.LINK.getValue());
	}

	public LinkMessage(String title, String description, String url) {
		this();
		Title = title;
		Description = description;
		Url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}

}
