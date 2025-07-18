package cc.sofast.framework.starter.redis;

import cc.sofast.framework.starter.redis.codec.ObjectMapperCustomizer;
import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.redis.codec.SofastGenericJackson2JsonRedisSerializer;
import cc.sofast.framework.starter.redis.redisson.SofastRedissonCustomizer;
import cc.sofast.framework.starter.redis.redisson.idempotent.IdempotentAspect;
import cc.sofast.framework.starter.redis.redisson.utils.RedisUtils;
import cc.sofast.framework.starter.redis.tempalte.RedisTemplateCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfigurationV2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;


/**
 * @author wxl
 */
@ConditionalOnClass(RedisConnectionFactory.class)
@EnableConfigurationProperties({RedisProperties.class})
@AutoConfiguration(before = {RedisAutoConfiguration.class, RedissonAutoConfigurationV2.class})
public class SofastRedisAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public ObjectMapperWrapper customObjectMapper(List<ObjectMapperCustomizer> customizers) {

        return new ObjectMapperWrapper(customizers);
    }

    @Configuration
    public static class SofastRedisTemplateConfiguration {

        /**
         * redisConnectionFactory 会被替换成 {@link RedissonAutoConfiguration#redissonConnectionFactory(RedissonClient)}
         */
        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                 ObjectProvider<RedisTemplateCustomizer> customizers,
                                                 ObjectProvider<ObjectMapperWrapper> wrappers) {
            RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);

            StringRedisSerializer keySerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(keySerializer);
            redisTemplate.setHashKeySerializer(keySerializer);

            ObjectMapper objectMapper = wrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
            SofastGenericJackson2JsonRedisSerializer valueSerializer = new SofastGenericJackson2JsonRedisSerializer(objectMapper);
            redisTemplate.setValueSerializer(valueSerializer);
            redisTemplate.setHashValueSerializer(valueSerializer);

            customizers.orderedStream().forEach((customizer) -> customizer.customize(redisTemplate));
            return redisTemplate;
        }

        /**
         * redisConnectionFactory 会被替换成 {@link RedissonAutoConfiguration#redissonConnectionFactory(RedissonClient)}
         */
        @Bean
        @ConditionalOnMissingBean
        public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectProvider<RedisTemplateCustomizer> customizers) {
            StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
            customizers.orderedStream().forEach((customizer) -> customizer.customize(redisTemplate));
            return redisTemplate;
        }
    }

    @Configuration
    public static class SofastRedissonConfiguration {

        @Bean
        public SofastRedissonCustomizer redissonCustomizer(ObjectProvider<ObjectMapperWrapper> wrappers) {
            ObjectMapper objectMapper = wrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
            return new SofastRedissonCustomizer(objectMapper);
        }

        @Bean
        public Map<String, Object> setRedissonClient(RedissonClient redissonClient) {
            RedisUtils.setRedissonClient(redissonClient);
            return Map.of();
        }

        @Bean
        public IdempotentAspect idempotentAspect() {

            return new IdempotentAspect();
        }

    }

    @Configuration
    public static class SofastRedisCacheConfiguration {


    }
}
