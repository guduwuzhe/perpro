package com.wang.perpro.service.cache.impl;


import com.wang.perpro.service.cache.CacheValueManager;
import com.wang.perpro.service.cache.IRedisValueCallBack;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @Author jinxue 2017年05月10 19时45分
 * @ProjectName:gallery
 * @Version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: jinxue
 * @modify by reason:{方法名}:{原因}
 */
@Slf4j
@Component
public class CacheValueManagerImpl<T> implements CacheValueManager<T> {

    @Autowired
    @Qualifier("cacheRedisTemplate")
    private RedisTemplate<String, byte[]> redisTemplate;

    @Autowired
    private FstRedisSerializer<T> redisSerializer;


    public ValueOperations<String, byte[]> getRedisValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public void set(final String key, T t) {
        getRedisValue().set(key, redisSerializer.serialize(t));
    }


    @Override
    public void set(final String key, T t, long expireTime) {
        getRedisValue().set(key, redisSerializer.serialize(t), expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(final String key, T t, long expireTime, TimeUnit timeUnit) {
        getRedisValue().set(key, redisSerializer.serialize(t), expireTime, timeUnit);
    }

    @Override
    public T get(final String key) {
        return redisSerializer.deserialize(getRedisValue().get(key));
    }


    @Override
    public T get(String key, long expireTime, IRedisValueCallBack<T> callBack, Object... dbKey) {
        return get(key, expireTime, TimeUnit.SECONDS, callBack, dbKey);
    }

    @Override
    public T get(String key, long expireTime, TimeUnit timeUnit, IRedisValueCallBack<T> callBack,
                 Object... dbKey) {
        T val = redisSerializer.deserialize(getRedisValue().get(key));
        if (val != null) {
            return val;
        }

        val = callBack.doInRedis(dbKey);
        if (val == null) {
            return null;
        }

        getRedisValue().set(key, redisSerializer.serialize(val), expireTime, timeUnit);
        return val;
    }


}
