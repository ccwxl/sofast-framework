package cc.sofast.framework.starter.redis.redisson;

import cc.sofast.framework.starter.redis.codec.RedissonJsonJacksonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.config.Config;
import org.redisson.config.Protocol;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;

/**
 * @author wxl
 */
public class SofastRedissonCustomizer implements RedissonAutoConfigurationCustomizer {
    private final ObjectMapper objectMapper;

    public SofastRedissonCustomizer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void customize(Config configuration) {
        configuration.setProtocol(Protocol.RESP3);
        configuration.setCodec(new RedissonJsonJacksonCodec(objectMapper));
    }
}
