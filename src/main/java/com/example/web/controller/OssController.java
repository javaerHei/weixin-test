package com.example.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.example.common.AliyunUtils;
import com.example.common.HttpClientUtils;
import com.example.common.JsonResult;
import com.example.common.JsonUtils;

/**
 * Oss Controller <br>
 * 创建日期：2016年12月23日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/oss")
public class OssController extends BaseController {

	@Value("${oss.endpoint}")
	private String endpoint;

	@Value("${oss.accessKeyId}")
	private String accessKeyId;

	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	
	@Value(value = "${oss.defaultBucket}")
	private String defaultBucket;
	
	@Autowired
	private AliyunUtils aliyunUtils;

	@Value(value = "${oss.cdn.folder}")
	private String ossFloder;

	/**
	 * 普通方式上传
	 * 
	 * @since 1.0
	 * @return <br>
	 */
	@RequestMapping({ "", "/index" })
	public String ossIndex() {
		return "oss";
	}

	/**
	 * JavaScript客户端签名直传
	 * @since 1.0
	 * @return <br>
	 */
	@RequestMapping("/js")
	public String jsIndex() {
		return "oss1";
	}

	
	/**
	 * 服务端签名后直传
	 * 
	 * @since 1.0
	 * @return <br>
	 */
	@RequestMapping("/server")
	public String serverIndex() {
		return "oss2";
	}

	/**
	 * 普通上传
	 * 
	 * @since 1.0
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(MultipartFile file) throws Exception {
		logger.info("开始上传文件: {}",file.getOriginalFilename());
		aliyunUtils.uploadFile(file, ossFloder + file.getOriginalFilename());
		logger.info("文件上传成功，link: {}",  "http://"+defaultBucket +"."+ endpoint+"/"+ ossFloder + file.getOriginalFilename());
		return JsonUtils.getSuccess();
	}

	/**
	 * 获取oss签名
	 * 
	 * @since 1.0
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 *             <br>
	 *             <b>作者： @author gongmingguo</b> <br>
	 *             创建时间：2016年12月27日 下午12:40:56
	 */
	@RequestMapping("/signature")
	@ResponseBody
	public String signature(HttpServletResponse response) throws UnsupportedEncodingException {
		/**
		 * accessid: 指的用户请求的accessid。注意仅知道accessid, 对数据不会有影响。
		 * host:指的是用户要往哪个域名发往上传请求。 policy：指的是用户表单上传的策略policy，是经过base64编码过的字符串。
		 * signature：是对上述第三个变量policy签名后的字符串。 expire：指的是当前上传策略失效时间
		 */
		String dir = ossFloder;
		String bucket = defaultBucket;
		String host = "http://" + bucket + "." + endpoint;

		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		long expireTime = 30;
		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

		String postPolicy = client.generatePostPolicy(expiration, policyConds);

		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = client.calculatePostSignature(postPolicy);
		JsonResult result = new JsonResult(true);
		JSONObject data = new JSONObject();
		data.put("accessid", accessKeyId);
		data.put("policy", encodedPolicy);
		data.put("signature", postSignature);
		data.put("dir", dir);
		data.put("host", host);
		data.put("expire", String.valueOf(expireEndTime / 1000));
		//
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");

		JSONObject callbackJson = new JSONObject();
		// TODO
		String callbackUrl = "http://gmgtest.ngrok.cc/oss/callback";
		callbackJson.put("callbackUrl", callbackUrl);
		callbackJson.put("callbackBody",
				"filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
		callbackJson.put("callbackBodyType", "application/x-www-form-urlencoded");

		// 回调函数
		data.put("callback", BinaryUtil.toBase64String(callbackJson.toJSONString().getBytes("utf-8")));
		result.addData(data);
		return result.toJson();
	}

	/**
	 * oss 文件上传回调函数
	 * 
	 * @throws Exception
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月27日 下午1:31:50
	 */
	@ResponseBody
	@RequestMapping("/callback")
	public void callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// GetPostBody(request.getInputStream(),Integer.parseInt(request.getHeader("content-length")));

		// String ossCallbackBody =
		// GetPostBody(request.getInputStream(),Integer.parseInt(request.getHeader("content-length")));

		String ossCallbackBody = getPostParamter(request);

		System.out.println("OSS Callback Body:" + ossCallbackBody);
		boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
		System.out.println("verify result:" + ret);
		if (ret) {
			response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
		} else {
			response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private String getPostParamter(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
		// 去掉最后一个空格
		queryString = queryString.substring(0, queryString.length() - 1);
		return queryString;
	}

	protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody) throws Exception {
		boolean ret = false;
		String autorizationInput = new String(request.getHeader("Authorization"));
		String pubKeyInput = request.getHeader("x-oss-pub-key-url");
		byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
		byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
		String pubKeyAddr = new String(pubKey);

		System.out.println(pubKeyAddr);

		if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
				&& !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
			System.out.println("pub key addr must be oss addrss");
			return false;
		}
		String retString = HttpClientUtils.get(pubKeyAddr);
		retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
		retString = retString.replace("-----END PUBLIC KEY-----", "");

		String queryString = request.getQueryString();
		// TODO
		// String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
		String authStr = "http://gmgtest.ngrok.cc/oss/callback";
		if (queryString != null && !queryString.equals("")) {
			authStr += "?" + queryString;
		}
		authStr += "\n" + ossCallbackBody;
		System.out.println("retString:" + retString);
		System.out.println("authStr:" + authStr);
		ret = doCheck(authStr, authorization, retString);
		return ret;
	}

	public static boolean doCheck(String content, byte[] sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(sign);
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public String GetPostBody(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				System.out.println(message);
				return new String(message, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
			throws IOException {
		String callbackFunName = request.getParameter("callback");
		response.addHeader("Content-Length", String.valueOf(results.length()));
		if (callbackFunName == null || callbackFunName.equalsIgnoreCase("")) {
			response.getWriter().println(results);
		} else {
			response.getWriter().println(callbackFunName + "( " + results + " )");
		}
		response.setStatus(status);
		response.flushBuffer();
	}
}
