package com.example.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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
@Configuration
public class ShiroConfiguration extends CachingConfigurerSupport {

	// 应用的时候将其配置成properties文件
	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	@Autowired
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
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(factory);

		// key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
		// 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
		// 或者JdkSerializationRedisSerializer序列化方式;
		
		// RedisSerializer<String> redisSerializer = new StringRedisSerializer();// Long类型不可以会出现异常信息;

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
	/*
	 * @Bean(name = "shiroEhcacheManager") public EhCacheManager
	 * getEhCacheManager() { EhCacheManager em = new EhCacheManager();
	 * em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml"); return em; }
	 */

	@Bean
	public RedisCacheManager redisCacheManager() {
		return new RedisCacheManager(redisTemplate(factory));
	}

	/*
	 * @Bean(name = "shiroCacheManager") public ShiroCacheManager
	 * shiroCacheManager() { ShiroCacheManager shiroCacheManager = new
	 * ShiroCacheManager();
	 * shiroCacheManager.setSimpleCacheManager(simpleCacheManager()); return
	 * shiroCacheManager; }
	 */

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

	@Bean
	public ServletContainerSessionManager servletContainerSessionManager() {
		return new ServletContainerSessionManager();
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setSessionManager(servletContainerSessionManager());
		dwsm.setRealm(shiroRealm());
		// <!-- 用户授权/认证信息Cache -->
		dwsm.setCacheManager(redisCacheManager());
		// dwsm.setCacheManager(getEhCacheManager());
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
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/user");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		filterChainDefinitionMap.put("/sa/**", "authc");
		filterChainDefinitionMap.put("/**", "anon");
		loadShiroFilterChain(shiroFilterFactoryBean);
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
		// authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		filterChainDefinitionMap.put("/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
		// anon：它对应的过滤器里面是空的,什么都没做
		filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取

		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/**", "anon");// anon 可以理解为不拦截

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

}
