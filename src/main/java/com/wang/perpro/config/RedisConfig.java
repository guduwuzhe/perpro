package com.wang.perpro.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.DefaultReferenceCodecProvider;
import org.redisson.codec.ReferenceCodecProvider;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p> 缓存 </p>
 *
 * @Author jinxue
 * @ProjectName:gallery
 * @Version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: jinxue
 * @modify by reason:{方法名}:{原因}
 */
//@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static final EventLoopGroup SHARED_EVENT_LOOP_GROUP;

    static {
        EventLoopGroup eventLoopGroup;
        try {
            eventLoopGroup = new EpollEventLoopGroup();
        } catch (Throwable e) {
            eventLoopGroup = new NioEventLoopGroup();
        }
        SHARED_EVENT_LOOP_GROUP = eventLoopGroup;
    }

    @Autowired(required = false)
    private ReferenceCodecProvider codecProvider = new DefaultReferenceCodecProvider();

    @Autowired
    private RedisProperties properties;

    @Value("${spring.redis.id-generator.host:NOT_SET}")
    private String idGeneratorRedisHost;

    @Value("${spring.redis.id-generator.port:NOT_SET}")
    private String idGeneratorRedisPort;

    @Value("${spring.redis.id-generator.database:0}")
    private int idGeneratorRedisDatabase;

    @Value("${spring.redis.id-generator.maxActive:100}")
    private int idGeneratorRedisMaxActive;

    @Value("${spring.redis.id-generator.minIdle:50}")
    private int idGeneratorRedisMinIdle;

    @Value("${spring.redis.databases.cache:0}")
    private int cacheRedisDatabase;


    @Bean("cacheRedisTemplate")
    public RedisTemplate<String, byte[]> cacheRedisTemplate() {
        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory(cacheRedisDatabase));
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

   /* public RedisTemplate<String, byte[]> getRedisTemplate(
        @Qualifier("jedis.connection.factory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, byte[]> template = new RedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }



    @Bean("cacheRedisTemplate")
    public RedisTemplate<String, Object> cacheRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory(cacheRedisDatabase));
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }*/

   /* @Bean(value = "stringRedisTemplate")
    public RedisTemplate<String, Long> getRedisStringTemplate(
            @Qualifier("jedis.connection.factory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        //设置key
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer((new GenericToStringSerializer<>(Long.class)));
        //开启事务
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
*/
    private JedisConnectionFactory createConnectionFactory(int database) {
        return createConnectionFactory(properties.getHost(), database);
    }

    private JedisConnectionFactory createConnectionFactory(String hostname, int database) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig());

        applyProperties(connectionFactory);
        connectionFactory.setHostName(hostname);
        connectionFactory.setDatabase(database);

        connectionFactory.afterPropertiesSet();

        //log.warn("init redis: host={}, database={}", hostname, database);

        return connectionFactory;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();

        RedisProperties.Pool props = this.properties.getJedis().getPool();
        if (props == null) {
            return config;
        }

        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait().toMillis());
        return config;
    }

    protected final JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
        factory.setHostName(this.properties.getHost());
        factory.setPort(this.properties.getPort());
        if (this.properties.getPassword() != null) {
            factory.setPassword(this.properties.getPassword());
        }

        if (this.properties.isSsl()) {
            factory.setUseSsl(true);
        }
        factory.setDatabase(this.properties.getDatabase());
        if (this.properties.getTimeout().getSeconds() > 0) {
            factory.setTimeout((int)this.properties.getTimeout().toMillis());
        }
        return factory;
    }

    private Config createRedissonConfig(int database) {
        Config config = new Config();
        config.setEventLoopGroup(SHARED_EVENT_LOOP_GROUP);
        config.setUseLinuxNativeEpoll(SHARED_EVENT_LOOP_GROUP instanceof EpollEventLoopGroup);

        config.setCodec(StringCodec.INSTANCE);
        config.setReferenceCodecProvider(codecProvider);

        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://" + properties.getHost() + ":" + properties.getPort());
        serverConfig.setDatabase(database);

        RedisProperties.Pool props = this.properties.getJedis().getPool();
        if (props != null) {
            serverConfig.setConnectionPoolSize(props.getMaxActive());
            serverConfig.setConnectionMinimumIdleSize(props.getMinIdle());
        }
        return config;
    }

    private Config createIdGeneratorRedissonConfig() {
        Config config = new Config();
        EventLoopGroup eventLoopGroup;
        try {
            eventLoopGroup = new EpollEventLoopGroup();
        } catch (Throwable e) {
            eventLoopGroup = new NioEventLoopGroup();
        }
        config.setEventLoopGroup(eventLoopGroup);
        config.setUseLinuxNativeEpoll(eventLoopGroup instanceof EpollEventLoopGroup);

        config.setCodec(StringCodec.INSTANCE);
        config.setReferenceCodecProvider(codecProvider);

        SingleServerConfig serverConfig = config.useSingleServer();
        if ("NOT_SET".equals(idGeneratorRedisHost) || "NOT_SET".equals(idGeneratorRedisPort)) {
            serverConfig.setAddress("redis://" + properties.getHost() + ":" + properties.getPort());
        } else {
            serverConfig.setAddress("redis://" + idGeneratorRedisHost + ":" + idGeneratorRedisPort);
        }
        serverConfig.setDatabase(idGeneratorRedisDatabase);
        serverConfig.setConnectionPoolSize(idGeneratorRedisMaxActive);
        serverConfig.setConnectionMinimumIdleSize(idGeneratorRedisMinIdle);
        //log.warn("init id generator redis: address={}, database={}, maxActive={}, minIdle={}", serverConfig.getAddress(), idGeneratorRedisDatabase, idGeneratorRedisMaxActive, idGeneratorRedisMinIdle);
        return config;
    }
}
