package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	// 便捷的路径映射
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/test").setViewName("/test");
		registry.addViewController("/ws").setViewName("/ws");

	}
}
