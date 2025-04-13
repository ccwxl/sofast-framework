package cc.sofast.framework.starter.redis.redisson;

import cc.sofast.framework.starter.redis.codec.RedissonJsonJacksonCodec;
import cc.sofast.framework.starter.redis.redisson.starter.RedissonAutoConfigurationCustomizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

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
