package com.example.weixin;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.common.HttpClientUtils;
import com.example.weixin.menu.Menu;

/**
 * 微信api接口服务 <br>
 * 创建日期：2016年11月9日 <br>
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Service
public class WeiXinApiService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 微信号测试
	@Value("${weixin.appId}")
	private  String weixinAppId;
	
	@Value("${weixin.appSecret}")
	private  String weixinAppSecret;

	@Resource(name = "stringRedisTemplate")
	private ValueOperations<Object, Object> valOpsStr;

	public String getAccessToken() {
		String accessToken = null;
		Object cache = valOpsStr.get("weixin_access_token_" + weixinAppId);
		if (cache == null) {
			try {
				String jsonStr = HttpClientUtils
						.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weixinAppId
								+ "&secret=" + weixinAppSecret);
				JSONObject result = JSONObject.parseObject(jsonStr);
				String accessTokenCache = result.getString("access_token");
				if (accessTokenCache != null) {
					long expiresIn = result.getLongValue("expires_in");
					valOpsStr.set("weixin_access_token", result.get("access_token"), expiresIn, TimeUnit.SECONDS);
					accessToken = accessTokenCache;
					logger.info("获取token结果：{}", accessToken);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			accessToken = cache.toString();
		}
		return accessToken;
	}

	public String getUserInfo(String openId) {
		String accessToken = getAccessToken();
		String result = null;
		try {
			result = HttpClientUtils.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken
					+ "&openid=" + openId + "&lang=zh_CN");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void createButton(Menu menu) {
		JSONArray buttonArray = new JSONArray();
		buttonArray.add(menu);
		createAllButton(buttonArray);
	}

	public void createAllButton(JSONArray menuJsonArray) {

		JSONObject jsonButton = new JSONObject();
		jsonButton.put("button", menuJsonArray);

		String accessToken = getAccessToken();
		String result = null;
		try {
			result = HttpClientUtils.postParameters(
					"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, jsonButton.toString());
			logger.info(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
