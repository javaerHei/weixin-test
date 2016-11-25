package com.example.web.shiro;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bean.User;
import com.example.common.Digests;
import com.example.common.Encodes;
import com.example.dto.RoleDto;
import com.example.dto.UserDto;
import com.example.service.UserService;

/**
 * shiro自定义realm <br>
 * 可参考SimpleAccountRealm实现 创建日期：2016年11月21日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class ShiroRealmImpl extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRealmImpl.class);

	@Autowired
	private UserService userService;

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// TODO 验证验证码
		String captcha = token.getCaptcha();
		if(StringUtils.isEmpty(captcha)) {
			throw new AuthenticationException("msg:请输入验证码");
		}
		logger.info("验证码:{}", captcha);
		Object validateCode = UserUtils.getSession().getAttribute("validateCode");
		if (validateCode != null) {
			if (!validateCode.toString().equals(captcha)) {
				throw new AuthenticationException("msg:验证码错误");
			}
			UserUtils.getSession().removeAttribute("validateCode");
		}

		logger.info(
				"验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		// 查出是否有此用户
		User user = userService.findByName(token.getUsername());
		if (user != null) {
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验，可以加盐
			ByteSource salt = ByteSource.Util.bytes(token.getUsername().getBytes());
			return new SimpleAuthenticationInfo(new Principal(user, false), user.getPassword(), salt, getName());
		}
		return null;
	}

	/**
	 * 权限认证，为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试：本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
	 * @see 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-
	 *      shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("##################执行Shiro权限认证##################");
		// 获取当前登录输入的用户名，等价于(String)
		Principal principal = (Principal) getAvailablePrincipal(principals);
		String loginName = principal.getUsername();
		// 到数据库查是否有此对象
		UserDto user = userService.findByName(loginName);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		if (user != null) {
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			info.setRoles(user.getRolesName());
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
			List<RoleDto> roleList = user.getRoleList();
			for (RoleDto role : roleList) {
				List<String> permissionsName = role.getPermissionsName();
				if (permissionsName != null) {
					info.addStringPermissions(permissionsName);
				}
			}
			// 或者按下面这样添加
			// 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
			// simpleAuthorInfo.addRole("admin");
			// 添加权限
			// simpleAuthorInfo.addStringPermission("admin:manage");
			// logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
			return info;
		}
		// 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}
	
	public void clearUserCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals() ;
		super.doClearCache(principals);
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private Long id; // 编号
		private String username; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.mobileLogin = mobileLogin;
		}

		public Long getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

	}

	// 生成安全的密码，
	public static String entryptPassword(String plainPassword, byte[] salt) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt);
		return Encodes.encodeHex(hashPassword);
	}

	public static void main(String[] args) {
		String salt = "jack";
		ByteSource byteSource = ByteSource.Util.bytes(salt.getBytes());
		System.out.println(entryptPassword("123456", byteSource.getBytes()));
	}

}
