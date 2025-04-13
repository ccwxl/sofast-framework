package cc.sofast.framework.starter.redis;

import cc.sofast.framework.starter.redis.codec.ObjectMapperCustomizer;
import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.redis.redisson.SofastRedissonAutoConfigurationCustomizer;
import cc.sofast.framework.starter.redis.redisson.starter.RedissonAutoConfiguration;
import cc.sofast.framework.starter.redis.redisson.starter.RedissonAutoConfigurationCustomizer;
import cc.sofast.framework.starter.redis.tempalte.RedisTemplateCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * @author wxl
 */
@EnableCaching
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfiguration(before = {RedisAutoConfiguration.class, RedissonAutoConfiguration.class})
public class SofastRedisAutoConfiguration {

    @Bean
    public ObjectMapperWrapper customObjectMapper(List<ObjectMapperCustomizer> customizers) {

        return new ObjectMapperWrapper(customizers);
    }

    @Configuration
    public static class RedisTemplateConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory,
                                                 ObjectProvider<ObjectMapperWrapper> objectMapperWrappers,
                                                 ObjectProvider<RedisTemplateCustomizer> customizers) {
            RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(factory);

            StringRedisSerializer keySerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(keySerializer);
            redisTemplate.setHashKeySerializer(keySerializer);

            ObjectMapper objectMapper = objectMapperWrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
            GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
            redisTemplate.setValueSerializer(valueSerializer);
            redisTemplate.setHashValueSerializer(valueSerializer);

            customizers.orderedStream().forEach((customizer) -> customizer.customize(redisTemplate));
            return redisTemplate;
        }

        @Bean
        @ConditionalOnMissingBean
        public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectProvider<RedisTemplateCustomizer> customizers) {
            StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
            customizers.orderedStream().forEach((customizer) -> customizer.customize(redisTemplate));
            return redisTemplate;
        }
    }

    @Configuration
    public static class RedissonConfiguration {

        @Bean
        public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer(ObjectProvider<ObjectMapperWrapper> objectMapperWrappers) {
            ObjectMapper objectMapper = objectMapperWrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
            return new SofastRedissonAutoConfigurationCustomizer(objectMapper);
        }
    }

    @Configuration
    public static class RedisCacheConfiguration {


    }
}
