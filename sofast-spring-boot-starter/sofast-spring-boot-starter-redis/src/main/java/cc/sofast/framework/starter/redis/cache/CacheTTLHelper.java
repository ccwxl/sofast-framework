package cc.sofast.framework.starter.redis.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 缓存TTL Helper,用于框架内部CacheTTL支持
 *
 * @author wxl
 */
@Slf4j
@SuppressWarnings("all")
public class CacheTTLHelper {

    public static final ScopedValue<Long> DURATION = ScopedValue.newInstance();

    public static <R> R call(Long duration, Callable<Object> op) {
        try {
            Object call = ScopedValue.where(DURATION, duration).call(op);
            if (call == null) {
                return null;
            }
            return (R) call;
        } catch (Exception e) {
            log.error("redis ttl duration failed", e);
            throw new RuntimeException(e);
        }
    }

    public static Long get() {
        return DURATION.get();
    }
}
