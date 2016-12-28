package com.example.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;

/**
 *
 * 阿里云OSS工具类
 * 
 */
@Component
public class AliyunUtils {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${oss.endpoint}")
	private String endpoint;

	@Value(value = "${oss.accessKeyId}")
	private String accessKeyId;

	@Value(value = "${oss.accessKeySecret}")
	private String accessKeySecret;

	@Value(value = "${oss.defaultBucket}")
	private String defaultBucket;

	/**
	 * 上传文件至阿里云
	 * 
	 * @since 1.0
	 * @param file 文件
	 * @param keyName 文件名
	 * @return String
	 * @throws IOException <br>
	 */
	public String uploadFile(MultipartFile file, String keyName) throws IOException {
		long startTime = System.currentTimeMillis();
		logger.info(String.format("文件%s开始上传到阿里云OSS.....", file.getOriginalFilename()));

		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());

		client.putObject(defaultBucket, keyName, file.getInputStream(), objectMetadata);

		logger.info(String.format("文件%s成功上传到阿里云OSS，耗时：%s", file.getOriginalFilename(),
				(System.currentTimeMillis() - startTime) + ""));
		return keyName;
	}

	/**
	 * @since 1.0
	 * @param key key
	 * @param input input
	 * @throws IOException IOException
	 */
	public void uploadFile(String key, InputStream input) throws IOException {
		if (key.startsWith("/"))
			key = key.substring(1);
		long startTime = System.currentTimeMillis();
		logger.info(String.format("文件%s开始上传到阿里云OSS.....", key));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(input.available());
		client.putObject(defaultBucket, key, input, metadata);
		logger.info(String.format("文件%s成功上传到阿里云OSS，耗时：%s", key, (System.currentTimeMillis() - startTime) + ""));
	}

	/**
	 * 删除文件
	 * 
	 * @since 1.0
	 * @param bucketName 桶名称
	 * @param keyName <br>
	 */
	public void delFile(String bucketName, String keyName) {
		long startTime = System.currentTimeMillis();
		logger.info(String.format("删除文件%s.....", keyName));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		if (!StringUtils.isNotBlank(bucketName)) {
			bucketName = defaultBucket;
		}
		client.deleteObject(bucketName, keyName);
		logger.info(String.format("删除文件%s，耗时：%s", keyName, (System.currentTimeMillis() - startTime) + ""));
	}

	/**
	 * 删除文件
	 * 
	 * @since 1.0
	 * @param keyName keyName
	 */
	public void delFile(String keyName) {

		long startTime = System.currentTimeMillis();
		logger.info(String.format("删除文件%s.....", keyName));
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		if (keyName.startsWith("/"))
			keyName = keyName.substring(1);
		client.deleteObject(defaultBucket, keyName);
		logger.info(String.format("删除文件%s，耗时：%s", keyName, (System.currentTimeMillis() - startTime) + ""));

	}

	/**
	 * 获取文件
	 * 
	 * @since 1.0
	 * @param keyName 名称
	 * @return <br>
	 */
	public GetObjectRequest getObjectMetadata(String keyName) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(defaultBucket, keyName);
		return getObjectRequest;
	}

}
