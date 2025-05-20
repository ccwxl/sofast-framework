package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wxl
 */
@FunctionalInterface
public interface ObjectMapperCustomizer {
    void customize(ObjectMapper objectMapper);
}
