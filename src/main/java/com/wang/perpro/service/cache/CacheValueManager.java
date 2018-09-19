package com.wang.perpro.service.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxiaoliang
 * @date 2018-09-10 下午6:29
 * @description:
 */
public interface CacheValueManager<T> {
  void set(final String key, T t);

  void set(final String key, T t, long expireTime);

  void set(final String key, T t, long expireTime, TimeUnit timeUnit);

  T get(final String key);

  T get(final String key, long expireTime, IRedisValueCallBack<T> callBack, Object... dbKey);

  T get(final String key, long expireTime, TimeUnit timeUnit, IRedisValueCallBack<T> callBack,
      Object... dbKey);
}
