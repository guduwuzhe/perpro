package com.wang.perpro.service.cache.impl;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

/**
 * 使用Fst框架实现的redis序列化类.
 * <p>
 *
 * @param <T> the generic type
 * @author jinxue
 * @version 1.0版本
 */
@Component
public class FstRedisSerializer<T> implements RedisSerializer<T> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    private FSTConfiguration fstConfiguration = FSTConfiguration.createFastBinaryConfiguration();

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return EMPTY_ARRAY;
        }
        try {
            return fstConfiguration.asByteArray(t);
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (T) fstConfiguration.asObject(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }


    public FSTConfiguration getFstConfiguration() {
        return fstConfiguration;
    }

    public void setFstConfiguration(FSTConfiguration fstConfiguration) {
        this.fstConfiguration = fstConfiguration;
    }

}
