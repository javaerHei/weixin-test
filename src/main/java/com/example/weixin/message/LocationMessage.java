package com.example.weixin.message;

/**
 * 地理位置消息 <br>
 * 创建日期：2016年11月9日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class LocationMessage extends BaseMessage {

	private String Location_X;// 地理位置维度
	private String Location_Y;// 地理位置经度
	private String Scale;// 地图缩放大小
	private String Label;// 地理位置信息

	public LocationMessage() {
		super(EMessageType.LOCATION.getValue());
	}

	public LocationMessage(String location_X, String location_Y, String scale, String label) {
		this();
		Location_X = location_X;
		Location_Y = location_Y;
		Scale = scale;
		Label = label;
	}

	/**
	 * @return the location_X
	 */
	public String getLocation_X() {
		return Location_X;
	}

	/**
	 * @param location_X
	 *            the location_X to set
	 */
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	/**
	 * @return the location_Y
	 */
	public String getLocation_Y() {
		return Location_Y;
	}

	/**
	 * @param location_Y
	 *            the location_Y to set
	 */
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return Scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(String scale) {
		Scale = scale;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return Label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		Label = label;
	}

}
