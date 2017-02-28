package com.shaw.myblog.common.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class RedisManager {

	private static Logger logger = LoggerFactory.getLogger("RedisManager");

	private static JedisPool pool = null;
	private static String IP_ADDRESS = null;
	
//	static代码块：
//	static关键字还有一个比较关键的作用就是 用来形成静态代码块以优化程序性能;
//	static块可以置于类中的任何地方,类中可以有多个static块;
//	在类初次被加载的时候,会按照static块的顺序来执行每个static块,并且只会执行一次.
	static {
		try {
			System.out.println("==============>>>> RedisManager redis pool init start");
			Properties props = new Properties();
			props.load(RedisManager.class.getClassLoader().getResourceAsStream("redis.properties"));
			IP_ADDRESS = props.getProperty("redis.ip");
			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置池配置项值
			config.setTestWhileIdle(false);
			config.setMaxTotal(Integer.valueOf(props.getProperty("redis.pool.maxTotal")));
			config.setMaxIdle(Integer.valueOf(props.getProperty("redis.pool.maxIdle")));
			config.setMaxWaitMillis(Long.valueOf(props.getProperty("redis.pool.maxWaitMillis")));
			config.setTestOnBorrow(Boolean.valueOf(props.getProperty("redis.pool.testOnBorrow")));
			config.setTestOnReturn(Boolean.valueOf(props.getProperty("redis.pool.testOnReturn")));
			String password = props.getProperty("redis.password");
			logger.info("======>>redis config : ip:" + IP_ADDRESS + ",password:" + password);
			
			if (StringUtils.isBlank(password)) {
				pool = new JedisPool(config, IP_ADDRESS, Integer.valueOf(props.getProperty("redis.port")), Integer.valueOf(props.getProperty("redis.timeout")));
			} else {
				pool = new JedisPool(config, IP_ADDRESS, Integer.valueOf(props.getProperty("redis.port")), Integer.valueOf(props.getProperty("redis.timeout")), password);
			}
			System.out.println("================>>>> RedisManager  redis pool init end================= ");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Error("IP:" + IP_ADDRESS + ",设置redis服务器出错", e);
		}
	}
	
   static Jedis getRedis() {
        if (pool != null) {
            return pool.getResource();
        }
        return null;
    }
    //获取连接
    public static Jedis getRedis(int index) {
        if (pool != null) {
            Jedis jedis = pool.getResource();
            jedis.select(index);
            return jedis;
        }
        return null;
    }
    //返还到连接池
    public static void close(Jedis jedis) {
        if (jedis != null) {
        	jedis.close(); 
        }
    }
    
    
    
    
}
