package com.shaw.myblog.common.redis;

import redis.clients.jedis.Jedis;
/**
 * Description:回调方法
 */
public interface RedisCallback<T> {
	public T call(Jedis jedis,Object params);
}
