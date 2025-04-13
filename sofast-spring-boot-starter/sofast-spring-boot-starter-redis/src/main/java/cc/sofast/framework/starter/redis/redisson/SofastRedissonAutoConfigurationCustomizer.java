package cc.sofast.framework.starter.redis.redisson;

import cc.sofast.framework.starter.redis.codec.RedissonJsonJacksonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;

/**
 * @author wxl
 */
public class SofastRedissonAutoConfigurationCustomizer implements RedissonAutoConfigurationCustomizer {
    private final ObjectMapper objectMapper;

    public SofastRedissonAutoConfigurationCustomizer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void customize(Config configuration) {
        configuration.setCodec(new RedissonJsonJacksonCodec(objectMapper));
    }
}
