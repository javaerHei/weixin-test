package com.example.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bean.User;
import com.example.dao.UserDao;
import com.example.web.shiro.FormAuthenticationFilter;
import com.github.cage.Cage;
import com.github.cage.token.RandomTokenGenerator;

/**
 * Shiro测试Controller <br>
 * 创建日期：2016年11月17日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Controller
public class ShiroController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 如果登录失败从request中获取认证异常信息
		//Object exceptionClass = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		
		Object exceptionMessage = request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		redirectAttributes.addFlashAttribute("messageType", "danger");
		redirectAttributes.addFlashAttribute("message", exceptionMessage);
		
		String username = request.getParameter("username");
		logger.info("对用户[ {} ]进行登录验证..验证未通过：{}",username, exceptionMessage);
		
		/*
		 * if (exceptionClass != null) {
			String exceptionClassName = exceptionClass.toString();
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
				redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
			} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
				redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
			} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
				logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
				redirectAttributes.addFlashAttribute("message", "账户已锁定");
			} else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
				logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
				redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
			} else {
				redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "服务器开小差了");
		}
		*/

		// 此方法不处理登录成功，shiro认证成功会自动跳转到上个请求路径
		return "redirect:/login";
	}

	/*
	 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public
	 * String logout(RedirectAttributes redirectAttributes) { //
	 * 使用权限管理工具进行用户的退出，跳出登录，给出提示信息 SecurityUtils.getSubject().logout();
	 * redirectAttributes.addFlashAttribute("message", "您已安全退出"); return
	 * "redirect:/index"; }
	 */

	@RequestMapping("/403")
	public String unauthorizedRole() {
		logger.info("------没有权限-------");
		return "403";
	}

	/**
	 * 主页
	 * 
	 * @since 1.0
	 * @return <br>
	 *         <b>作者： @author gongmingguo</b> <br>
	 *         创建时间：2016年11月23日 下午2:37:31
	 */
	@RequestMapping(value = { "/index", "/home" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = { "/user" })
	public String getUserList(HttpServletRequest request, Map<String, Object> model) {
		model.put("userList", userDao.getList());
		// redis session 测试
		HttpSession session = request.getSession();
		session.setAttribute("redis session test", "heheiheei");

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			String value = cookie.getValue();
			System.out.println("cookie name : " + name);
			System.out.println("cookie value : " + value);
		}
		// 查看当前登录用户信息
		Long currentUserId = getCurrentUserId();
		System.out.println(currentUserId);

		return "user";
	}

	@RequestMapping("/user/edit/{userid}")
	public String getUserList() {
		logger.info("------进入用户信息修改-------");
		return "user_edit";
	}

	@RequestMapping("/login-validateCode")
	public void validateCodeGenerator(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		OutputStream os = null;

		RandomTokenGenerator tokenGenerator = new RandomTokenGenerator(null, 4);
		Cage cage = new Cage(null, null, null, null, Cage.DEFAULT_COMPRESS_RATIO, tokenGenerator, null);

		os = response.getOutputStream();
		String code = cage.getTokenGenerator().next();

		cage.draw(code, os);
		HttpSession session = request.getSession();
		session.setAttribute("validateCode", code);
		os.close();
	}
}
