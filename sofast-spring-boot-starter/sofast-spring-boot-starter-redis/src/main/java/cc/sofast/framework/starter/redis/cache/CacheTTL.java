package cc.sofast.framework.starter.redis.cache;

import java.lang.annotation.*;

/**
 * 缓存有效时长注解,搭配Cacheable 使用
 *
 * @author wxl
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheTTL {

    /**
     * 缓存有效时长, 单位: 秒
     */
    long value() default -1;
}
