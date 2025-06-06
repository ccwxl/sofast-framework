package cc.sofast.framework.starter.redis.codec;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

/**
 * @author wxl
 */
@Slf4j
@SuppressWarnings("all")
public class RedisDeserializeHelper {

    public static final ScopedValue<Class<?>> TYPE = ScopedValue.newInstance();
    public static final ScopedValue<Type> GENERIC_TYPE = ScopedValue.newInstance();

    public static <R> R call(Class<R> clazz, Callable<Object> op) {
        try {
            Object call = ScopedValue.where(TYPE, clazz).call(op);
            if (call == null) {
                return null;
            }
            if (clazz.isAssignableFrom(call.getClass())) {
                return (R) call;
            }
            return (R) call;
        } catch (Exception e) {
            log.error("redis deserialize failed", e);
            throw new RuntimeException(e);
        }
    }

    public static <R> R call(Type clazz, Callable<Object> op) {
        try {
            Object call = ScopedValue.where(GENERIC_TYPE, clazz).call(op);
            if (call == null) {
                return null;
            }
            return (R) call;
        } catch (Exception e) {
            log.error("redis deserialize failed", e);
            throw new RuntimeException(e);
        }
    }

    public static Class<?> get() {
        return TYPE.get();
    }

    public static Type getGenericType() {
        return GENERIC_TYPE.get();
    }
}
