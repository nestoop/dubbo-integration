package com.example;

import cn.inspiry.tools.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * Created by botter
 *
 * @Date 27/11/16.
 * @description
 */
@Service
public class RedisService {

    private JedisPool cachePool;

    @Autowired
    public RedisService(@Value("${redis.hosts}") String hosts,
                        @Value("${redis.port}") Integer port,
                        @Value("${redis.password}") String password) {
        if (password != null && password.length() != 0) {
            cachePool = new JedisPool(new JedisPoolConfig(), hosts, port, 10 * 1000, password);
        } else {
            cachePool = new JedisPool(new JedisPoolConfig(), hosts, port);
        }
    }

    boolean addSet(String key, String value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }

        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.sadd(key, value);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            redisColse(jedis);
        }
    }

    Set<String> getSet(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            return null;
        } finally {
            redisColse(jedis);
        }
    }

    /**
     * 设置一个key的过期时间（单位：秒）
     *
     * @param key     key值
     * @param seconds 多少秒后过期
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间
     */
    public long expire(String key, int seconds) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }

        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.expire(key, seconds);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
        } finally {
            close(jedis);
        }
        return 0;
    }

    /**
     * 设置一个key在某个时间点过期
     *
     * @param key           key值
     * @param unixTimestamp unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间
     */
    public long expireAt(String key, int unixTimestamp) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.expireAt(key, unixTimestamp);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);
        } finally {
            close(jedis);
        }
        return 0;
    }

    /**
     * 截断一个List
     *
     * @param key   列表key
     * @param start 开始位置 从0开始
     * @param end   结束位置
     * @return 状态码
     */
    public String trimList(String key, long start, long end) {
        if (StringUtil.isEmpty(key)) {
            return "-";
        }
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.ltrim(key, start, end);
        } catch (Exception ex) {
            logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);

        } finally {
            close(jedis);
        }
        return "-";
    }

    /**
     * 检查Set长度
     *
     * @param key 键
     * @return 统计结果
     */
    public long countSet(String key) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.scard(key);
        } catch (Exception ex) {
            logger.error("countSet error.", ex);

        } finally {
            close(jedis);
        }
        return 0;
    }

    private void redisColse(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
