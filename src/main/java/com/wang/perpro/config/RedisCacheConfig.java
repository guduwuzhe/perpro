package com.wang.perpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author wangxiaoliang
 * @date 2018-09-12 上午11:00
 * @description:
 */
@Configuration
public class RedisCacheConfig {

  @Bean("cacheRedisTemplate")
  public RedisTemplate<String, byte[]> cacheRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, byte[]> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    //template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());

    return template;
  }

}
