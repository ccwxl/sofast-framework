package cc.sofast.framework.starter.beansearch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wxl
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
    /**
     * 查询条件
     */
    Condition value() default Condition.eq;

    /**
     * 数据库字段名称
     */
    String column() default "";
}
