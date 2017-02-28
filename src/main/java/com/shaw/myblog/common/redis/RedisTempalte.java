package com.shaw.myblog.common.redis;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Description:Redis 模板
 */
@Component
public class RedisTempalte {

    private static Logger logger = LoggerFactory.getLogger("RedisTempalte");
    
    protected <T> T execute(RedisCallback<T> callback, Object... args) {
        Jedis jedis = null;
        try {
            Object index = ((Object[]) args)[0];
            if (null != index && Integer.parseInt(index.toString()) > 0 && Integer.parseInt(index.toString()) < 16) {
                jedis = RedisManager.getRedis(Integer.parseInt(index.toString()));
            } else {
                jedis = RedisManager.getRedis();
            }
            return callback.call(jedis, args);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
           RedisManager.close(jedis);
        }
        return null;
    }

//    选择DB实例
    public void select(int index) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                int index = Integer.parseInt(((Object[]) parms)[0].toString());
                return jedis.select(index);
            }
        }, index);
    }


    public Boolean isConnected(int index) {
        return execute(new RedisCallback<Boolean>() {
            public Boolean call(Jedis jedis, Object params) {
                return jedis.isConnected();
            }
        }, index);
    }
//  Flushdb 命令用于清空当前数据库中的所有 key
    public String flushDB(int index) {
        return execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object params) {
                return jedis.flushDB();
            }
        }, index);
    }

    public Boolean exists(int index, String key) {
        return execute(new RedisCallback<Boolean>() {
            public Boolean call(Jedis jedis, Object params) {
                String key = ((Object[]) params)[1].toString();
                return jedis.exists(key);
            }
        }, index, key);
    }

    public Boolean hexists(int index, String mapKey, String attributeKey) {
        return execute(new RedisCallback<Boolean>() {
            public Boolean call(Jedis jedis, Object params) {
                String key = ((Object[]) params)[1].toString();
                String field = ((Object[]) params)[2].toString();
                return jedis.hexists(key, field);
            }
        }, index, mapKey, attributeKey);
    }

    /**
     * @Description: 获取Hash（哈希）
     */
    public String hget(int index, String key, String field) {
        return execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String field = ((Object[]) parms)[2].toString();
                return jedis.hget(key, field);
            }
        }, index, key, field);
    }


    public Set<String> keys(int index, String pattern) {
        return execute(new RedisCallback<Set<String>>() {
            public Set<String> call(Jedis jedis, Object params) {
                String pattern = ((Object[]) params)[1].toString();
                return jedis.keys(pattern);
            }
        }, index, pattern);
    }


    public Map<String, String> hgetAll(int index, String key) {
        return execute(new RedisCallback<Map<String, String>>() {
            public Map<String, String> call(Jedis jedis, Object params) {
                String key = ((Object[]) params)[1].toString();
                return jedis.hgetAll(key);
            }
        }, index, key);
    }

    public Long hdel(int index, String mapKey, String attributeKey) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object params) {
                String key = ((Object[]) params)[1].toString();
                String field = ((Object[]) params)[2].toString();
                return jedis.hdel(key, field);
            }
        }, index, mapKey, attributeKey);
    }

    /**
     * @Description: Hash（哈希）
     */
    public void hset(int index, String key, String field, String value) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String field = ((Object[]) parms)[2].toString();
                String value = ((Object[]) parms)[3].toString();
                jedis.hset(key, field, value);
                return null;
            }
        }, index, key, field, value);
    }

    /**
     * @Description:String（字符串）获取
     */
    public String get(int index, String key) {
        return execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                return jedis.get(key);
            }
        }, index, key);
    }

    public byte[] getByte(int index, String key) {
        return execute(new RedisCallback<byte[]>() {
            public byte[] call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                try {
                    return jedis.get(key.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }
        }, index, key);
    }

    /**
     * @Description: String（字符串）赋值
     */
    public void set(int index, String key, String value) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String value = ((Object[]) parms)[2].toString();
                jedis.set(key, value);
                return null;
            }
        }, index, key, value);
    }

    public void set(int index, String key, byte[] value) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                byte[] value = (byte[]) ((Object[]) parms)[2];
                try {
                    jedis.set(key.getBytes("UTF-8"), value);
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }
        }, index, key, value);
    }

    /**
     * @Description:seconds:过期时间（单位：秒）
     */
    public void set(int index, String key, String value, int seconds) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String value = ((Object[]) parms)[2].toString();
                String seconds = ((Object[]) parms)[3].toString();
                jedis.setex(key, Integer.parseInt(seconds), value);
                return null;
            }
        }, index, key, value, seconds);
    }

    public void set(int index, String key, byte[] value, int seconds) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                byte[] value = (byte[]) ((Object[]) parms)[2];
                String seconds = ((Object[]) parms)[3].toString();
                try {
                    jedis.setex(key.getBytes("UTF-8"), Integer.parseInt(seconds), value);
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }
        }, index, key, value, seconds);
    }

    public Long expire(int index, String key, int seconds) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String seconds = ((Object[]) parms)[2].toString();
                return jedis.expire(key, Integer.parseInt(seconds));
            }
        }, index, key, seconds);
    }

    /**
     * @Description: 批量Set
     */
    public void setPipeLine(int index, List<RedisKVPO> list) {
        execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                Pipeline p = jedis.pipelined();
                @SuppressWarnings("unchecked")
                List<RedisKVPO> values = (List<RedisKVPO>) ((Object[]) parms)[1];
                for (RedisKVPO po : values) {
                    p.set(po.getK(), po.getV());
                }
                p.sync();
                return null;
            }
        }, index, list);
    }


    /**
     * @Description: 根据key删除
     */
    public Long del(int index, String key) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                return jedis.del(key);
            }
        }, index, key);
    }

    public String llen(int index, String key) {
        return execute(new RedisCallback<String>() {
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                return jedis.llen(key) + "";
            }
        }, index, key);
    }

    public Long lpush(int index, String key, String value) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String value = ((Object[]) parms)[2].toString();
                return jedis.lpush(key, value);
            }
        }, index, key, value);
    }

    public void lpushPipeLine(int index, String key, List<String> values) {
        execute(new RedisCallback<String>() {
            @SuppressWarnings("unchecked")
            public String call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                List<String> values = (List<String>) ((Object[]) parms)[2];
                Pipeline p = jedis.pipelined();
                for (String value : values) {
                    p.lpush(key, value);
                }
                p.sync();
                return null;
            }
        }, index, key, values);
    }

    public List<String> lrange(int index, String key, long start, long end) {
        return execute(new RedisCallback<List<String>>() {
            public List<String> call(Jedis jedis, Object parms) {
                Object[] ps = ((Object[]) parms);
                String key = ps[1].toString();
                long start = Long.parseLong(ps[2].toString());
                long end = Long.parseLong(ps[3].toString());
                return jedis.lrange(key, start, end);
            }
        }, index, key, start, end);
    }

    public Long incr(int index, String key) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                return jedis.incr(key);
            }
        }, index, key);
    }

    public Long sadd(int index, String key, String value) {
        return execute(new RedisCallback<Long>() {
            public Long call(Jedis jedis, Object parms) {
                String key = ((Object[]) parms)[1].toString();
                String value = ((Object[]) parms)[2].toString();
                return jedis.sadd(key, value);
            }
        }, index, key, value);
    }

    public Set<String> smembers(int index, String key) {
        return execute(new RedisCallback<Set<String>>() {
            public Set<String> call(Jedis jedis, Object parms) {
                Object[] ps = ((Object[]) parms);
                String key = ps[1].toString();
                return jedis.smembers(key);
            }
        }, index, key);
    }

    public List<String> brpop(int index, String key) {
        return execute(new RedisCallback<List<String>>() {
            public List<String> call(Jedis jedis, Object parms) {
                Object[] ps = ((Object[]) parms);
                String key = ps[1].toString();
                return jedis.brpop(0, key);
            }
        }, index, key);
    }

}
