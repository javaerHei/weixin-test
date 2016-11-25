package com.example.web.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.common.Servlets;

/**
 * 自定义shiro授权缓存管理类 <br>
 * 创建日期：2016年11月18日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class RedisCacheManager implements CacheManager {

	private static final String cacheKeyPrefix = "shiro_cache_";

	private RedisTemplate<Object, Object> redisTemplate;

	public RedisCacheManager(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache<K, V>(cacheKeyPrefix + name);
	}

	public class RedisCache<K, V> implements Cache<K, V> {

		private Logger logger = LoggerFactory.getLogger(getClass());

		private String cacheKeyName = null;

		public RedisCache(String cacheKeyName) {
			this.cacheKeyName = cacheKeyName;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V get(K key) throws CacheException {
			if (key == null) {
				return null;
			}
			V v = null;
			// 先从request中获取
			HttpServletRequest request = Servlets.getRequest();
			if (request != null) {
				v = (V) request.getAttribute(cacheKeyName);
				if (v != null) {
					return v;
				}
			}
			V value = null;
			// redis 中获取缓存
			Object object = redisTemplate.opsForHash().get(cacheKeyName, key);
			if (object != null) {
				value = (V) object;
			}

			logger.debug("get {} {} {}", cacheKeyName, key, request != null ? request.getRequestURI() : "");
			if (request != null && value != null) {
				request.setAttribute(cacheKeyName, value);
			}
			return value;
		}

		@Override
		public V put(K key, V value) throws CacheException {
			if (key == null) {
				return null;
			}
			redisTemplate.opsForHash().put(cacheKeyName, key, value);
			logger.debug("put {} {} = {}", cacheKeyName, key, value);
			return value;
		}

		// 用户正常退出时清除该用户缓存
		@SuppressWarnings("unchecked")
		@Override
		public V remove(K key) throws CacheException {
			V value = null;
			value = (V) redisTemplate.opsForHash().get(cacheKeyName, key);
			redisTemplate.opsForHash().delete(cacheKeyName, key);
			logger.debug("remove {} {}", cacheKeyName, key);
			return value;
		}

		@Override
		public void clear() throws CacheException {
			redisTemplate.delete(cacheKeyName);
			logger.debug("clear {}", cacheKeyName);
		}

		@Override
		public int size() {
			Long size = 0L;
			size = redisTemplate.opsForHash().size(cacheKeyName);
			logger.debug("size {} {} ", cacheKeyName, size);
			return size.intValue();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Set<K> keys() {
			Set<K> keys = new HashSet<>();
			Set<Object> set = redisTemplate.opsForHash().keys(cacheKeyName);
			for (Object key : set) {
				keys.add((K) key);
			}
			logger.debug("keys {} {} ", cacheKeyName, keys);
			return keys;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<V> values() {
			Collection<V> vals = Collections.emptyList();
			List<Object> values = redisTemplate.opsForHash().values(cacheKeyName);
			for (Object val : values) {
				vals.add((V) val);
			}
			logger.debug("values {} {} ", cacheKeyName, vals);
			return vals;
		}

	}

}
