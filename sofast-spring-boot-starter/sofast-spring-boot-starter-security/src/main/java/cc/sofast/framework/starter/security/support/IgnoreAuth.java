package cc.sofast.framework.starter.security.support;

import java.lang.annotation.*;

/**
 * 忽略认证
 *
 * @author wxl
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
