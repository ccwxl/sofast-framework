package cc.sofast.framework.starter.mybatis.join;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wxl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    Class<?> left();

    Class<?> right();

    String type();

    String leftKey();

    String rightKey();
}
