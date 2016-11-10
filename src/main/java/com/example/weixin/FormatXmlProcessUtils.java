package com.example.weixin;

import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.example.weixin.event.MenuEvent;
import com.example.weixin.message.BaseMessage;
import com.example.weixin.message.EMessageType;
import com.example.weixin.message.ImageMessage;
import com.example.weixin.message.LinkMessage;
import com.example.weixin.message.LocationMessage;
import com.example.weixin.message.ShortVideoMessage;
import com.example.weixin.message.TextMessage;
import com.example.weixin.message.VideoMessage;
import com.example.weixin.message.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * xml与对象转换工具
 * 
 */
public class FormatXmlProcessUtils {

	public static XStream xStream = new XStream(new XppDriver() {

		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out){
				boolean cdata = true;

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if(cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
		
	});
	
	/**
	 * 封装文字类的返回消息
	 * 
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public static String formatTextMessage(String to, String from, String content) {
		xStream.alias("xml", TextMessage.class);
		xStream.omitField(TextMessage.class, "MsgId");
		TextMessage entity = new TextMessage(to, from, content);
		String result = xStream.toXML(entity);
		return result;
	}

	public static BaseMessage getMessage(InputStream inputStream) {

		xStream.alias("xml", ReceiveXmlEntity.class);
		xStream.ignoreUnknownElements();
		ReceiveXmlEntity message = (ReceiveXmlEntity) xStream.fromXML(inputStream);
		EMessageType messageTypeEnum = EMessageType.value(message.getMsgType());
		BaseMessage resultMessage = null;
		switch (messageTypeEnum) {
		case TEXT:
			resultMessage = new TextMessage();
			break;
		case IMAGE:
			resultMessage = new ImageMessage();
			break;
		case VOICE:
			resultMessage = new VoiceMessage();
			break;
		case VIDEO:
			resultMessage = new VideoMessage();
			break;
		case SHORT_VIDEO:
			resultMessage = new ShortVideoMessage();
			break;
		case LOCATION:
			resultMessage = new LocationMessage();
			break;
		case LINK:
			resultMessage = new LinkMessage();
			break;
		case EVENT:
			resultMessage = new MenuEvent();
			break;
		default:
			break;
		}
		try {
			BeanUtils.copyProperties(resultMessage, message);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return resultMessage;
	}

}
