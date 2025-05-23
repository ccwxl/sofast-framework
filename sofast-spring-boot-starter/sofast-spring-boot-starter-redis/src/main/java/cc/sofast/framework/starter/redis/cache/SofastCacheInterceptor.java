package cc.sofast.framework.starter.redis.cache;

import cc.sofast.framework.starter.redis.codec.RedisDeserializeHelper;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author wxl
 */
public class SofastCacheInterceptor extends CacheInterceptor {

    @Override
    @Nullable
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        CacheOperationInvoker aopAllianceInvoker = () -> {
            try {
                return invocation.proceed();
            } catch (Throwable ex) {
                throw new CacheOperationInvoker.ThrowableWrapper(ex);
            }
        };

        Object target = invocation.getThis();
        Assert.state(target != null, "Target must not be null");
        try {
            Type genericReturnType = method.getGenericReturnType();
            return RedisDeserializeHelper.call(genericReturnType, () -> {
                CacheTTL annotation = method.getAnnotation(CacheTTL.class);
                if (annotation != null) {
                    return CacheTTLHelper.call(annotation.value(),
                            () -> SofastCacheInterceptor.this.execute(aopAllianceInvoker, target, method, invocation.getArguments()));
                }
                return SofastCacheInterceptor.this.execute(aopAllianceInvoker, target, method, invocation.getArguments());
            });
        } catch (CacheOperationInvoker.ThrowableWrapper th) {
            throw th.getOriginal();
        }
    }
}
