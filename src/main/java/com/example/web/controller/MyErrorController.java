package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面 <br>
 * 
 * @since 1.0
 * @version 1.0
 */
@Controller
public class MyErrorController {

	/**
	 * 错误页面
	 * 
	 * @since 1.0
	 * @param code
	 * @return <br>
	 */
	@RequestMapping("/error/{code}")
	public String error(@PathVariable("code") int code) {
		return "redirect:http://www.utouu.com/" + code + ".html";
	}

	/**
	 * 服务器错误
	 * 
	 * @since 1.0
	 * @return <br>
	 *         创建时间：2016年6月2日 上午10:16:16
	 */
	@RequestMapping("/server_error")
	public String serverError() {
		return "redirect:http://www.utouu.com/500.html";
	}

}
