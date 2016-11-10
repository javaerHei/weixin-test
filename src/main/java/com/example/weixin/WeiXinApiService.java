package com.example.weixin;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	// private static final String APP_ID = "wx5fbb358f8522f1dd";
	// private static final String APP_SECRET =
	// "fcc750adbd8f8ccf3dede394ac2f3a4c";

	// 微信号测试
	private static final String APP_ID_TEST = "wxb9824c8009905694";
	private static final String APP_SECRET_TEST = "c34bd9d2843d6b8b8312c902e4e94228";

	@Resource(name = "stringRedisTemplate")
	private ValueOperations<Object, Object> valOpsStr;

	public String getAccessToken() {
		String accessToken = null;
		Object cache = valOpsStr.get("weixin_access_token" + APP_ID_TEST);
		if (cache == null) {
			try {
				String jsonStr = HttpClientUtils
						.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID_TEST
								+ "&secret=" + APP_SECRET_TEST);
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
