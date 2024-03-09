package com.github.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Dooby Kim
 * @Date 2024/3/9 下午1:32
 * @Version 1.0
 * @Desc 对 RedisTemplate 的封装
 */
@Component
public class RedisOperator {

    // 在这里面务必要用 @Autowired 进行注入，或者使用 @Resource 注入时候，bean 的名称不要使用 redisTemplate
    // 原因在于，@Resource 注解会按照 bean byName 进行注入
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 返回 Key 的剩余生存时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置 Key 的过期时间，单位：秒
     *
     * @param key
     * @param timeout
     */
    public void setExpire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 查找所有符合给定 pattern 的 key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除一个 key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置 K-V，并指定超时时间（秒）
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置 K-V
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set if not exist；如果 Key 不存在，则设置，如果存在，则报错
     *
     * @param key
     * @param value
     */
    public void setnx(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * set if not exist；如果 Key 不存在，则设置，如果存在，则报错
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void setnx(String key, String value, long timeout) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 返回 Key 对应的 Value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量查询 key
     *
     * @param keys
     * @return
     */
    public List<String> multiGet(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 使用管道 pipeline 进行批量查询
     *
     * @param keys
     * @return
     */
    public List<Object> batchGet(List<String> keys) {
        List<Object> result = redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                StringRedisConnection src = (StringRedisConnection) redisConnection;
                for (String key : keys) {
                    src.get(key);
                }
                return null;
            }
        });

        return result;
    }

    /**
     * 返回哈希表 key 中给定域 field 对应的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 设置哈希表 key 中给定域  field 对应的值为 Value
     *
     * @param field
     * @param key
     * @param value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 删除哈希表 key 中一个或多个给定域，不存在的域将被忽略
     *
     * @param key
     * @param fields
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 返回哈希表 key 中，所有域和值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 将 value 值插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @return 执行 lpush 后，列表的长度
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 移除并返回列表 key 的头元素
     *
     * @param key
     * @return 返回 lpop 后，移除的列表 key 的头元素
     */
    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 将 value 插入到列表 key 的尾部
     *
     * @param key
     * @param value
     * @return 执行 rpush 后，列表 key 的长度
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 移除并返回列表 key 的末尾元素
     *
     * @param key
     * @return 返回 rpop 后，移除的列表 key 的末尾元素
     */
    public String rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

}
