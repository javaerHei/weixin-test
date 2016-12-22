package com.example.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Tomcat 错误页配置
 * <br>创建日期：2016年12月22日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Configuration
public class ErrorPageConfig {

	static final String PAGE_PREFIX = "/error/";

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(errorPages(HttpStatus.UNAUTHORIZED, HttpStatus.FORBIDDEN, HttpStatus.NOT_FOUND,
						HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY, HttpStatus.GATEWAY_TIMEOUT));

			}

			/**
			 * 根据状态码返回错误页
			 * 
			 * @since 1.0
			 * @param status
			 *            状态码
			 * @return 错误页 <br>
			 */
			private ErrorPage[] errorPages(HttpStatus... status) {
				ErrorPage[] errorPages = new ErrorPage[status.length];
				for (int i = 0; i < errorPages.length; i++) {
					errorPages[i] = new ErrorPage(status[i], PAGE_PREFIX + status[i].value());
				}
				return errorPages;
			}
		};
	}
}
