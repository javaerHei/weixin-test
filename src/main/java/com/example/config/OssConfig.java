package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * oss  <br>
 * 创建日期：2016年12月26日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Configuration
public class OssConfig {

	private static Logger logger = LoggerFactory.getLogger(OssConfig.class);

	@Value("${mns.accountEndpoint}")
	private String accountEndpoint;

	@Value("${mns.accessKeyId}")
	private String accessKeyId;

	@Value("${mns.accessKeySecret}")
	private String accessKeySecret;


}
