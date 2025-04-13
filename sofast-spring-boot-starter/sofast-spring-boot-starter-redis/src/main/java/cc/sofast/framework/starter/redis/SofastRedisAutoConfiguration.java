package cc.sofast.framework.starter.redis;

import cc.sofast.framework.starter.redis.codec.ObjectMapperCustomizer;
import cc.sofast.framework.starter.redis.codec.ObjectMapperWrapper;
import cc.sofast.framework.starter.redis.redisson.SofastRedissonAutoConfigurationCustomizer;
import cc.sofast.framework.starter.redis.tempalte.RedisTemplateCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.redisson.spring.starter.RedissonAutoConfigurationV2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
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
         * redisConnectionFactory 是 {@link RedissonAutoConfiguration#redissonConnectionFactory(RedissonClient)}
         */
        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                 ObjectProvider<ObjectMapperWrapper> objectMapperWrappers,
                                                 ObjectProvider<RedisTemplateCustomizer> customizers) {
            RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);

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

        /**
         * redisConnectionFactory 是 {@link RedissonAutoConfiguration#redissonConnectionFactory(RedissonClient)}
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
        public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer(ObjectProvider<ObjectMapperWrapper> objectMapperWrappers) {
            ObjectMapper objectMapper = objectMapperWrappers.getIfAvailable(ObjectMapperWrapper::new).getObjectMapper();
            return new SofastRedissonAutoConfigurationCustomizer(objectMapper);
        }
    }

    @Configuration
    public static class SofastRedisCacheConfiguration {


    }
}
