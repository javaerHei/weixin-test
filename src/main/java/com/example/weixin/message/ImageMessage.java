package com.example.weixin.message;

/**
 * 图片消息 <br>
 * 创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class ImageMessage extends BaseMessage {

	private String PicUrl;// 图片链接
	private String MediaId;// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

	public ImageMessage() {
		super(EMessageType.IMAGE.getValue());
	}

	public ImageMessage(String picUrl, String mediaId) {
		this();
		PicUrl = picUrl;
		MediaId = mediaId;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return PicUrl;
	}

	/**
	 * @param picUrl
	 *            the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return MediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
