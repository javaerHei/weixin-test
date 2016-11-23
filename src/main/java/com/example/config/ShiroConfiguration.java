package com.example.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.example.web.shiro.FormAuthenticationFilter;
import com.example.web.shiro.RedisCacheManager;
import com.example.web.shiro.ShiroRealmImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Shiro 配置 <br>
 * 创建日期：2016年11月17日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@EnableRedisHttpSession
@Configuration
public class ShiroConfiguration extends CachingConfigurerSupport {

	// 应用的时候将其配置成properties文件
	//private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	private RedisConnectionFactory factory;

	/**
	 * 缓存shiro 权限
	 * @since 1.0 
	 * @param factory
	 * @return
	 * <br><b>作者： @author gongmingguo</b>
	 * <br>创建时间：2016年11月18日 下午2:46:51
	 */
	@Bean
	public RedisTemplate<Object, Object> shiroCacheRedisTemplate(RedisConnectionFactory factory) {
		this.factory = factory;
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(factory);

		// 配置json格式value
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);

		redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		return redisTemplate;
	}

	@Bean(name = "shiroRealmImpl")
	public ShiroRealmImpl shiroRealm() {
		return new ShiroRealmImpl();
	}

	@Bean
	public RedisCacheManager shiroRedisCacheManager() {
		return new RedisCacheManager(shiroCacheRedisTemplate(factory));
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		// 默认即使用servletContainerSessionManager管理session（spring-session包装的session）
		// dwsm.setSessionManager(new ServletContainerSessionManager());
		dwsm.setRealm(shiroRealm());
		// <!-- 用户授权/认证信息Cache -->
		dwsm.setCacheManager(shiroRedisCacheManager());
		return dwsm;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(defaultWebSecurityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
		//loginUrl认证提交地址
		shiroFilterFactoryBean.setLoginUrl("/login");
	    shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		loadShiroFilterChain(shiroFilterFactoryBean);
		// 过滤器
		Map<String, Filter> filters = new LinkedHashMap<>();
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setLoginUrl("/login");
		filters.put("authc", formAuthenticationFilter);
		
		shiroFilterFactoryBean.setFilters(filters);
		
		return shiroFilterFactoryBean;
	}

	/**
	 * 加载shiroFilter权限控制规则（从数据库读取然后配置）
	 *
	 * @create 2016年1月14日
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		/////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	
		// anon：它对应的过滤器里面是空的,什么都没做
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/error/**", "anon");
		filterChainDefinitionMap.put("/server_error", "anon");
		
		
		filterChainDefinitionMap.put("/login-validateCode", "anon");
		
		// 静态资源
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/bower_components/**", "anon");
		
		filterChainDefinitionMap.put("/logout", "logout");
		// authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		
		filterChainDefinitionMap.put("/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
		filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

}
