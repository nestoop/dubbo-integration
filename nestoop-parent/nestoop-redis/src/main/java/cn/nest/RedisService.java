package cn.nest;

import cn.inspiry.tools.StringUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * Created by botter
 *
 * @Date 27/11/16.
 * @description
 */
@Service
public class RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisService.class);


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

    /**指定的哈希集中所有字段的key值
    *
            * @param domain 域名
    * @return keySet
    */

    public Set<String> hkeys(String domain) {
        Jedis jedis = null;
        Set<String> retList = null;
        try {
            jedis = cachePool.getResource();
            retList = jedis.hkeys(domain);
        } catch (Exception ex) {
            logger.error("hkeys error.", ex);

        } finally {
            close(jedis);
        }
        return retList;
    }

    /**
     * 返回 domain 指定的哈希key值总数
     *
     * @param domain 域名
     * @return 返回hash key 值总数
     */
    public long lenHset(String domain) {
        Jedis jedis = null;
        long retList = 0;
        try {
            jedis = cachePool.getResource();
            retList = jedis.hlen(domain);
        } catch (Exception ex) {
            logger.error("hkeys error.", ex);

        } finally {
            close(jedis);
        }
        return retList;
    }

    /**
     * 设置排序集合
     *
     * @param key   键值
     * @param score 序列值
     * @param value 值
     * @return 设置排序是否成功
     */
    public boolean setSortedSet(String key, long score, String value) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.zadd(key, score, value);
            return true;
        } catch (Exception ex) {
            logger.error("setSortedSet error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    /**
     * 获得排序集合（倒排序）
     *
     * @param key         键值
     * @param startScore  开始数
     * @param endScore    结束数
     * @param orderByDesc 排序字段
     * @return 排序集合
     */
    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (orderByDesc) {
                return jedis.zrevrangeByScore(key, endScore, startScore);
            } else {
                return jedis.zrangeByScore(key, startScore, endScore);
            }
        } catch (Exception ex) {
            logger.error("getSoredSet error.", ex);

        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 计算排序长度
     *
     * @param key        键值
     * @param startScore 开始数
     * @param endScore   结束数
     * @return 数量
     */
    public long countSoredSet(String key, long startScore, long endScore) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            Long count = jedis.zcount(key, startScore, endScore);
            return count == null ? 0L : count;
        } catch (Exception ex) {
            logger.error("countSoredSet error.", ex);

        } finally {
            close(jedis);
        }
        return 0L;
    }

    /**
     * 删除排序集合
     *
     * @param key   键值
     * @param value 值
     * @return 是否成功
     */
    public boolean delSortedSet(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            long count = jedis.zrem(key, value);
            return count > 0;
        } catch (Exception ex) {
            logger.error("delSortedSet error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    /**
     * 获得排序集合
     *
     * @param key         键值
     * @param startRange  开始数
     * @param endRange    结束数
     * @param orderByDesc 排序依据
     * @return 排序集合
     */
    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (orderByDesc) {
                return jedis.zrevrange(key, startRange, endRange);
            } else {
                return jedis.zrange(key, startRange, endRange);
            }
        } catch (Exception ex) {
            logger.error("getSoredSetByRange error.", ex);

        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 获得排序打分
     *
     * @param key 键值
     * @return 排序位置
     */
    public Double getScore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception ex) {
            logger.error("getSoredSet error.", ex);

        } finally {
            close(jedis);
        }
        return null;
    }

    public boolean set(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.setex(key, second, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    public boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    public boolean set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    public boolean set(Map<String, String> keyValues) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            for (Map.Entry<String, String> keyValue : keyValues.entrySet()) {
                jedis.set(keyValue.getKey(), keyValue.getValue());
            }
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.get(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);

        } finally {
            close(jedis);
        }
        return "";
    }

    public byte[] get(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.get(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);

        } finally {
            close(jedis);
        }
        return null;
    }

    public <T> T get(String key, Class<T> tCls) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            String result = jedis.get(key);
            if (StringUtil.isNotEmpty(result)) {
                return JSON.parseObject(result, tCls);
            }
        } catch (Exception ex) {
            logger.error("get error.", ex);

        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 获取 指定 class 的列表
     *
     * @param key  键值
     * @param tCls 类型
     * @return 列表
     */
    public <T> List<T> getClassList(String key, Class<T> tCls) {
        assert StringUtil.isNotEmpty(key);
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            String result = jedis.get(key);
            if (StringUtil.isNotEmpty(result)) {
                return JSON.parseArray(result, tCls);
            }
        } catch (Exception ex) {
            logger.error("get error.", ex);

        } finally {
            close(jedis);
        }
        return new ArrayList<T>();
    }

    public boolean del(String key) {
        assert StringUtil.isNotEmpty(key);
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception ex) {
            logger.error("del error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    public boolean del(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception ex) {
            logger.error("del error.", ex);

        } finally {
            close(jedis);
        }
        return false;
    }

    /**
     * 将指定主键key的value值加一
     *
     * @param key redis健值
     * @return 返回增加后的值
     */
    public long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.incr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);

        } finally {
            close(jedis);
        }
        return 0;
    }

    /**
     * 将指定主键key的value值减一
     *
     * @param key key redis健值
     */
    public long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.decr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);
        } finally {
            close(jedis);
        }
        return 0;
    }

    private void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("returnResource error.", e);
        }
    }

    /**
     * 添加Map<String,String> 类型的缓存
     *
     * @param key   redis健值
     * @param value redis value
     * @return 添加成功
     */
    public boolean setMap(String key, Map<String, String> value) {
        assert StringUtil.isNotEmpty(key);
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            jedis.hmset(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
        } finally {
            close(jedis);
        }
        return false;
    }

    /**
     * 添加Map<String,Object> 类型的缓存（会将Object转换成JSON)
     *
     * @param key   redis健值
     * @param value redis value
     * @return 添加成功
     */
    public boolean setObjectMap(String key, Map<String, ?> value) {
        if (StringUtil.isNotEmpty(key) && value != null && !value.isEmpty()) {
            Map<String, String> ret = new HashMap<String, String>();
            for (Map.Entry<String, ?> entry : value.entrySet()) {
                ret.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
            }
            return setMap(key, ret);
        }
        return false;
    }

    /**
     * 获取指定类型的Map值
     *
     * @param key    键值
     * @param tClass 类型
     * @param <T>    指定类型
     * @return Map
     */
    public <T> Map<String, T> getMap(String key, Class<T> tClass) {
        Map<String, T> ret = new HashMap<String, T>();
        for (Map.Entry<String, String> stringStringEntry : getMap(key).entrySet()) {
            ret.put(stringStringEntry.getKey(), JSON.parseObject(stringStringEntry.getValue(), tClass));
        }
        return ret;
    }

    /**
     * 获取指定类型的Map值
     *
     * @param key    键值
     * @param tClass 类型
     * @param <T>    指定类型
     * @return Map
     */
    public <T> List<T> getMapList(String key, String mapKey, Class<T> tClass) {
        List<String> mapValue = getMapValue(key, mapKey);
        if (mapValue != null && !mapValue.isEmpty()) {
            return JSON.parseArray(mapValue.get(0), tClass);
        }
        return new ArrayList<T>();
    }

    /**
     * 获取指定类型的Map值
     *
     * @param key     键值
     * @param tClass  类型
     * @param mapKeys mapKey
     * @param <T>     指定类型
     * @return Map
     */
    public <T> List<T> getMapList(String key, Class<T> tClass, String... mapKeys) {
        if (mapKeys != null) {
            List<String> mapValue = getMapValue(key, mapKeys);
            if (mapValue != null && !mapValue.isEmpty()) {
                return JSON.parseArray(mapValue.get(0), tClass);
            }
        }
        return new ArrayList<T>();
    }

    /**
     * 获取指定类型的Map值
     *
     * @param key    键值
     * @param tClass 类型
     * @param <T>    指定类型
     * @return Map
     */
    public <T> Map<String, List<T>> getListMap(String key, Class<T> tClass) {
        Map<String, List<T>> ret = new HashMap<String, List<T>>();
        for (Map.Entry<String, String> stringStringEntry : getMap(key).entrySet()) {
            ret.put(stringStringEntry.getKey(), JSON.parseArray(stringStringEntry.getValue(), tClass));
        }
        return ret;
    }

    /**
     * 获取Map大小
     *
     * @param key 键值
     * @return Map大小
     */
    public long getMapSize(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hlen(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);
            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 取出Map 所有key
     *
     * @param key redis键值
     * @return 取出Map 所有key
     */
    public Set<String> getMapKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hkeys(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);

            return new HashSet<String>();
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定Map 中指定key的值
     *
     * @param key     redis键值
     * @param mapKeys map key
     * @return 获取指定Map 中指定key的值
     */
    public List<String> getMapValue(String key, String... mapKeys) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hmget(key, mapKeys);
        } catch (Exception ex) {
            logger.error("get error.", ex);

            return new ArrayList<String>();
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取所有Map所有值
     *
     * @param key redis键值
     * @return 获取所有Map所有值
     */
    public List<String> getMapValues(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hvals(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);

            return new ArrayList<String>();
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除Map中的元素
     *
     * @param key    redis 键值
     * @param mapKey map key
     * @return 删除数量
     */
    public long deleteMapValue(String key, String... mapKey) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hdel(key, mapKey);
        } catch (Exception ex) {
            logger.error("get error.", ex);

            return 0L;
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取Map
     *
     * @param key redis键值
     * @return Map
     */
    public Map<String, String> getMap(String key) {
        Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);

        } finally {
            close(jedis);
        }
        return new HashMap<String, String>();
    }

    /**
     * 获取阻塞锁
     *
     * @param lockKey key
     * @param seconds 有效时间（秒） 当传入0时，无失效时间
     * @return
     */
    public synchronized boolean lock(String lockKey, int seconds) {
        if (StringUtil.isEmpty(lockKey)) {
            return false;
        }
        Jedis jedis = cachePool.getResource();
        long lockExpireTime = System.currentTimeMillis() + seconds * 1000 + 1;//锁超时时间
        if (jedis.setnx(lockKey, String.valueOf(lockExpireTime)) == 1) { // 获取到锁
            //成功获取到锁, 设置相关标识
            jedis.expire(lockKey, seconds);
            RedisLockThread.setThreadMap(Thread.currentThread().getId(), Thread.currentThread());
            return true;
        } else {
            return false;
        }
    }

    //释放锁
    public synchronized void unLock(String lockKey) {
        Jedis jedis = null;
        try {
            // 避免删除非自己获取得到的锁
            Thread thread = RedisLockThread.getThread(Thread.currentThread().getId());
            if (thread != null) {
                jedis = cachePool.getResource();
                jedis.del(lockKey);
                //删除MAP中的线程记录
                RedisLockThread.removeThread(Thread.currentThread().getId());
            }
        } finally {
            close(jedis);
        }
    }

    private void redisColse(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
