package cc.sofast.framework.starter.redis.cache;

import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.redis.codec.SofastGenericJackson2JsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author wxl
 */
@AutoConfiguration
@EnableConfigurationProperties({CacheProperties.class, SofastCacheProperties.class})
public class SofastRedisCacheConfiguration {

    private static final String COLON = ":";

    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties, ObjectProvider<ObjectMapperWrapper> wrappers) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.computePrefixWith(cacheName -> {
            String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
            if (StringUtils.hasText(keyPrefix)) {
                keyPrefix = keyPrefix.lastIndexOf(COLON) == -1 ? keyPrefix + COLON : keyPrefix;
                return keyPrefix + cacheName + COLON;
            }
            return cacheName + COLON;
        });
        ObjectMapper objectMapper = wrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
        // 设置使用 JSON 序列化方式
        SofastGenericJackson2JsonRedisSerializer redisSerializer = new SofastGenericJackson2JsonRedisSerializer(objectMapper);
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));

        // 设置 CacheProperties.Redis 的属性
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                               RedisCacheConfiguration redisCacheConfiguration,
                                               SofastCacheProperties sofastCacheProperties) {
        // 创建 RedisCacheWriter 对象
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(sofastCacheProperties.getRedisScanBatchSize()));
        // 创建 TenantRedisCacheManager 对象
        return new RedisCacheManager(cacheWriter, redisCacheConfiguration);
    }
}
