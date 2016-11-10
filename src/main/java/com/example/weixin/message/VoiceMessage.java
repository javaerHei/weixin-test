package com.example.weixin.message;

/**
 * 语音消息
 * <br>创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class VoiceMessage extends BaseMessage {

	private String MediaId;// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String Format;// 语音格式，如amr，speex等,一般为amr
	
	private String Recognition;//语音识别结果，使用UTF8编码。

	public VoiceMessage() {
		super(EMessageType.VOICE.getValue());
	}

	public VoiceMessage(String mediaId, String format, String recognition) {
		this();
		MediaId = mediaId;
		Format = format;
		Recognition = recognition;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return MediaId;
	}

	/**
	 * @param mediaId the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return Format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}

	/**
	 * @return the recognition
	 */
	public String getRecognition() {
		return Recognition;
	}

	/**
	 * @param recognition the recognition to set
	 */
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	
}
