package cc.sofast.framework.starter.redis.cache;

import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * 支持Cache自定义过期时间
 *
 * @author wxl
 */
public class SofastTTLFunction implements RedisCacheWriter.TtlFunction {

    private final Duration duration;

    SofastTTLFunction(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Duration getTimeToLive(Object key, Object value) {
        Long l = CacheTTLHelper.get();
        if (l != null) {
            return Duration.ofSeconds(l);
        }
        return duration;
    }
}
