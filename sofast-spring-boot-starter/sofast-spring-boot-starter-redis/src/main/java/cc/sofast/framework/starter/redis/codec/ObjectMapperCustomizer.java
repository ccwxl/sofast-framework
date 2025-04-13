package cc.sofast.framework.starter.redis.codec;

import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author wxl
 */
@FunctionalInterface
public interface ObjectMapperCustomizer {
    void customize(JsonMapper.Builder builder);
}
