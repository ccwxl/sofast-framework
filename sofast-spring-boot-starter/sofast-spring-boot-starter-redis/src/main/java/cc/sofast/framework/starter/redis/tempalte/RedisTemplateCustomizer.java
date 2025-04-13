package cc.sofast.framework.starter.redis.tempalte;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wxl
 */
public interface RedisTemplateCustomizer {

    /**
     * redisTemplate 自定义
     *
     * @param redisTemplate RedisTemplate<String, Object>
     */
    void customize(RedisTemplate<?, ?> redisTemplate);
}
